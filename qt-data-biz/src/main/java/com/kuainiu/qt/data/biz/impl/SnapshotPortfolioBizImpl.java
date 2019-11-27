package com.kuainiu.qt.data.biz.impl;

import com.kuainiu.qt.data.biz.SnapshotPortfolioBiz;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioInformationRatioProcessorInBean;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.common.util.RedisUtil;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.AidcQryService;
import com.kuainiu.qt.data.service.PortfolioService;
import com.kuainiu.qt.data.service.SnapshotPortfolioService;
import com.kuainiu.qt.data.service.bean.PortfolioSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;
import com.kuainiu.qt.data.service.code.SnapshotPortfolioCode;
import com.kuainiu.qt.data.service.http.AidcSHHttp;
import com.kuainiu.qt.data.util.BizBeanUtils;
import com.kuainiu.qt.framework.common.util.CalculateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.kuainiu.qt.data.common.util.CommonConstant.TRANS_DAYS_A_YEAR;

@Service
@Slf4j
public class  SnapshotPortfolioBizImpl implements SnapshotPortfolioBiz {
    @Autowired
    SnapshotPortfolioService snapshotPortfolioService;

    @Autowired
    AidcSHHttp aidcSHHttp;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    AidcQryService aidcQryService;

    @Override
    public SnapshotPortfolioOutBean qryLastBeforeOpenMarket(SnapshotPortfolioInBean inBean) throws BizException {
        SnapshotPortfolioOutBean  outBean;
        try {
            SnapshotPortfolioSerBean serBean = snapshotPortfolioService.findLastBeforeOpenMarket(inBean.getPortfolioCode());
            outBean = BizBeanUtils.buildSnapshotPortfolioOutBean(serBean);
        } catch (ServiceException e) {
            throw new BizException(QtDataRspCode.ERR_PORTFOLIOSNAPSHOT_INFO_QRY_FAIL, e.getMsg());
        }
        return outBean;
    }

    @Override
    public void recordPortfolio(PortfolioInformationRatioProcessorInBean jobParam) {
        Date belongTime = QtDateUtils.getZeroSecondTime(QtDateUtils.getTestTime());
        log.info("[Biz][Portfolio] InformationRatioProcessor start,belongTime={}", belongTime);
        try {
            if (!snapshotPortfolioService.needRun() || !jobParam.isForce()) {
                log.warn("today is not trans day or curr time is not in open market!");
            }
            List<PortfolioSerBean> portfolioSerBeanList = portfolioService.findDistinctPortfolioCode();
            log.info("[Biz][Portfolio]snapshot={}", portfolioSerBeanList);
            for (PortfolioSerBean portfolioSerBean : portfolioSerBeanList) {
                String portfolioCode = portfolioSerBean.getPortfolioCode();
                calcPortfolio(portfolioCode, belongTime);
            }
        } catch (ServiceException e) {
            log.info("[Biz][Portfolio] InformationRatioProcessor fail", e);
        }
        log.info("[Biz][Portfolio] InformationRatioProcessor end");
    }

    @Override
    public void calcPortfolio(String portfolioCode, Date belongTime) throws ServiceException {
        SnapshotPortfolioSerBean snapshotPortfolio = new SnapshotPortfolioSerBean();
        try {
            snapshotPortfolio = snapshotPortfolioService.getPortfolioByBelongTime(portfolioCode, belongTime);
            BigDecimal tr = snapshotPortfolio.getTotalReturns();
            Integer T = aidcQryService.getPortfolioRundays(snapshotPortfolio.getPortfolioCode(), snapshotPortfolio.getStartDate());
            BigDecimal rm = aidcQryService.qryRm(snapshotPortfolio);
            BigDecimal baseReturns = calcBaseReturns(snapshotPortfolio, rm);
            BigDecimal balanceReturns = calcBalanceReturns(tr, baseReturns);
            BigDecimal infoRatio = calcInfoRatio(portfolioCode, belongTime, balanceReturns, T);
            snapshotPortfolio.setBaseReturns(baseReturns);
            snapshotPortfolio.setBaseRealtimeReturns(rm);
            snapshotPortfolio.setBalanceReturns(balanceReturns);
            snapshotPortfolioService.updateReturnsFields(snapshotPortfolio);
            snapshotPortfolio.setInformationRatio(infoRatio);
            snapshotPortfolio.setErrorFlag(SnapshotPortfolioCode.SUCCESS.getCode());
            snapshotPortfolioService.updateInfoRatio(snapshotPortfolio);
        } catch (Exception e) {
            log.error("calc snapshotPortfolio information fail " + snapshotPortfolio);
        }
    }

    private BigDecimal calcBaseReturns(SnapshotPortfolioSerBean snapshotPortfolio, BigDecimal rm) throws ServiceException {
        //昨天的+现在
        return CalculateUtils.sumBigDecimal(rm, qryHistoryBaseReturns(snapshotPortfolio));
    }

    private BigDecimal calcBalanceReturns(BigDecimal tr, BigDecimal baseReturns) {
        return CalculateUtils.sumBigDecimal(tr, baseReturns);
    }

    private BigDecimal calcInfoRatio(String portfolioCode, Date belongTime, BigDecimal balanceReturns, Integer T) throws ServiceException {
        SnapshotPortfolioSerBean queryStdSP = new SnapshotPortfolioSerBean();
        queryStdSP.setPortfolioCode(portfolioCode);
        queryStdSP.setEndBelongTime(belongTime);
        BigDecimal std = snapshotPortfolioService.getBalanceReturnStdByPortfolioCode(queryStdSP).getStd();
        double result = balanceReturns.doubleValue() / (std.doubleValue() / Math.sqrt(T.doubleValue()) * Math.sqrt(TRANS_DAYS_A_YEAR.doubleValue()));
        return new BigDecimal(result);
    }

    private BigDecimal qryHistoryBaseReturns(SnapshotPortfolioSerBean snapshotPortfolio) throws ServiceException {
        snapshotPortfolio.setErrorFlag("");
        snapshotPortfolio.setEndBelongTime(QtDateUtils.getOpenMarket());
        SnapshotPortfolioSerBean item = snapshotPortfolioService.getLastBeforeOpenMarket(snapshotPortfolio);
        return item.getBaseReturns();
    }
}
