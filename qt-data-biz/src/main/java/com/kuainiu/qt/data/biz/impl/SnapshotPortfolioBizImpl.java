package com.kuainiu.qt.data.biz.impl;

import com.kuainiu.qt.data.biz.SnapshotPortfolioBiz;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioInformationRatioProcessorInBean;
import com.kuainiu.qt.data.common.util.CommonConstant;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.common.util.RedisUtil;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.AidcQryService;
import com.kuainiu.qt.data.service.PortfolioService;
import com.kuainiu.qt.data.service.SnapshotPortfolioService;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;
import com.kuainiu.qt.data.service.bean.aidc.RmReqSerBean;
import com.kuainiu.qt.data.service.bean.aidc.RmSerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioSerBean;
import com.kuainiu.qt.data.service.code.SnapshotPortfolioCode;
import com.kuainiu.qt.data.service.http.AidcSHHttp;
import com.kuainiu.qt.data.util.BizBeanUtils;
import com.kuainiu.qt.data.util.BizReqSerBeanUtils;
import com.kuainiu.qt.data.util.CalculateUtil;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.framework.common.util.CalculateUtils;
import com.kuainiu.qt.trans.facade.code.PortfolioStatusCode;
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
    public SnapshotPortfolioOutBean findByBelongTimeAndErrorFlag(SnapshotPortfolioInBean inBean) throws BizException {
        SnapshotPortfolioOutBean  outBean;
        try {
            SnapshotPortfolioReqSerBean reqSerBean = BizReqSerBeanUtils.buildSnapshotPortfolioReqSerBean(inBean);
            SnapshotPortfolioSerBean serBean = snapshotPortfolioService.findByBelongTimeAndErrorFlag(reqSerBean);
            outBean = BizBeanUtils.buildSnapshotPortfolioOutBean(serBean);
        } catch (ServiceException e) {
            throw new BizException(QtDataRspCode.ERR_PORTFOLIOSNAPSHOT_INFO_QRY_FAIL, e.getMsg());
        }
        return outBean;
    }

    @Override
    public void recordPortfolio(PortfolioInformationRatioProcessorInBean jobParam) {
        Date belongTime = QtDateUtils.getZeroSecondTime(QtDateUtils.getCurrDate());
        log.info("[Biz][Portfolio] InformationRatioProcessor recordPortfolio,belongTime={}", belongTime);
        try {
            if (!snapshotPortfolioService.needRun() || !jobParam.isForce()) {
                log.warn("today is not trans day or curr time is not in open market!");
            }
            PortfolioReqSerBean reqSerBean = new PortfolioReqSerBean();
            reqSerBean.setStatus(PortfolioStatusCode.VALID.getCode());
            List<PortfolioSerBean> portfolioSerBeanList = portfolioService.findAll(reqSerBean);
            log.info("[Biz][Portfolio]snapshot={}", portfolioSerBeanList);
            for (PortfolioSerBean portfolioSerBean : portfolioSerBeanList) {
                String portfolioCode = portfolioSerBean.getPortfolioCode();
                calcPortfolio(portfolioCode, belongTime);
            }
        } catch (ServiceException e) {
            log.info("[Biz][Portfolio] InformationRatioProcessor fail", e);
        }
    }

    @Override
    public void calcPortfolio(String portfolioCode, Date belongTime) throws ServiceException {
        SnapshotPortfolioSerBean snapshotPortfolio = new SnapshotPortfolioSerBean();
        try {
            snapshotPortfolio = snapshotPortfolioService.getPortfolioByBelongTime(portfolioCode, belongTime, SnapshotPortfolioCode.UNFINISHED.getCode());
            BigDecimal tr = snapshotPortfolio.getTotalReturns();
            Integer T = aidcQryService.getPortfolioRundays(snapshotPortfolio.getPortfolioCode(), snapshotPortfolio.getStartDate());
            BigDecimal rm = qryRm(belongTime);
            BigDecimal baseReturns = calcBaseReturns(snapshotPortfolio, rm);
            BigDecimal balanceReturns = calcBalanceReturns(tr, baseReturns);
            SnapshotPortfolioReqSerBean reqSerBean = new SnapshotPortfolioReqSerBean();
            BeanMapUtils.map(snapshotPortfolio, reqSerBean);
            reqSerBean.setBaseReturns(baseReturns);
            reqSerBean.setBaseRealtimeReturns(rm);
            reqSerBean.setBalanceReturns(balanceReturns);
            snapshotPortfolioService.updateReturnsFields(reqSerBean);
            BigDecimal infoRatio = calcInfoRatio(portfolioCode, belongTime, balanceReturns, T);
            reqSerBean.setInformationRatio(infoRatio);
            reqSerBean.setErrorFlag(SnapshotPortfolioCode.SUCCESS.getCode());
            snapshotPortfolioService.updateInfoRatio(reqSerBean);
        } catch (Exception e) {
            log.error("calc snapshotPortfolio information fail " + snapshotPortfolio);
            throw new ServiceException(QtDataRspCode.ERR_CALC_INFO_RATIO, e.getMessage());
        }
    }

    private BigDecimal qryRm(Date belongTime) throws ServiceException {
        BigDecimal rm = CommonConstant.BIG_DECIMAL_ZERO;
        RmReqSerBean rmReqSerBean = new RmReqSerBean();
        rmReqSerBean.setDatetime(QtDateUtils.converToYMDHms(belongTime));
        RmSerBean rmSerBean = aidcQryService.qryRm(rmReqSerBean);
        log.info("[AIDC] qryRm serBean ={}", rmSerBean);
        rm = rmSerBean.getData();
        return rm;
    }

    private BigDecimal calcBaseReturns(SnapshotPortfolioSerBean snapshotPortfolio, BigDecimal rm) throws ServiceException {
        //昨天的+现在
        return CalculateUtils.sumBigDecimal(rm, qryHistoryBaseReturns(snapshotPortfolio));
    }

    private BigDecimal calcBalanceReturns(BigDecimal tr, BigDecimal baseReturns) {
        return CalculateUtils.sumBigDecimal(tr, baseReturns);
    }

    private BigDecimal calcInfoRatio(String portfolioCode, Date belongTime, BigDecimal balanceReturns, Integer T) throws ServiceException {
        SnapshotPortfolioReqSerBean queryStdSP = new SnapshotPortfolioReqSerBean();
        queryStdSP.setPortfolioCode(portfolioCode);
        queryStdSP.setEndBelongTime(belongTime);
        BigDecimal std = snapshotPortfolioService.getBalanceReturnStdByPortfolioCode(queryStdSP).getStd();
        log.info("[Service] getBalanceReturnStd , std={}",std);
        if(CalculateUtil.isZero(std)) {
            log.error("[Service] getStd error, std={}",std);
        }
        double result = balanceReturns.doubleValue() / (std.doubleValue() / Math.sqrt(T.doubleValue()) * Math.sqrt(TRANS_DAYS_A_YEAR.doubleValue()));
        return new BigDecimal(result);
    }

    private BigDecimal qryHistoryBaseReturns(SnapshotPortfolioSerBean snapshotPortfolio) throws ServiceException {
        SnapshotPortfolioReqSerBean reqSerBean = new SnapshotPortfolioReqSerBean();
        reqSerBean = BizReqSerBeanUtils.buildHistoryBaseReturnsReqSerBean(snapshotPortfolio);
        log.info("[Service] findByBelongTimeAndErrorFlag request={}",snapshotPortfolio);
        SnapshotPortfolioSerBean item = snapshotPortfolioService.getByBelongTimeAndErrorFlag(reqSerBean);
        log.info("[Service] findByBelongTimeAndErrorFlag response={}", item);
        return item.getBaseReturns();
    }
}
