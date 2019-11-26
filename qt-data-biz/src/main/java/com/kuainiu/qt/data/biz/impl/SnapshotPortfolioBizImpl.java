package com.kuainiu.qt.data.biz.impl;

import com.kuainiu.qt.data.biz.SnapshotPortfolioBiz;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioInformationRatioProcessorInBean;
import com.kuainiu.qt.data.common.util.CommonConstant;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.PortfolioService;
import com.kuainiu.qt.data.service.SnapshotPortfolioService;
import com.kuainiu.qt.data.service.bean.PortfolioSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;
import com.kuainiu.qt.data.service.code.SnapshotPortfolioCode;
import com.kuainiu.qt.data.service.http.AidcCDHttp;
import com.kuainiu.qt.data.service.http.AidcSHHttp;
import com.kuainiu.qt.data.service.http.request.StockEarningRate300Request;
import com.kuainiu.qt.data.service.http.request.aidc.AidcTradeDateRequest;
import com.kuainiu.qt.data.service.http.response.StockEarningRate300Response;
import com.kuainiu.qt.data.util.BizBeanUtils;
import com.kuainiu.qt.framework.common.util.CalculateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    AidcCDHttp portfolioHttp;

    @Autowired
    PortfolioService portfolioService;

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
        Date belongTime = QtDateUtils.getZeroSecondTime(QtDateUtils.getCurrDate());
        log.info("[Biz][Portfolio] InformationRatioProcessor start,belongTime={}", belongTime);
        try {
            List<PortfolioSerBean> portfolioSerBeanList = portfolioService.findDistinctPortfolioCode();
            log.info("[Biz][Portfolio]snapshot={}", portfolioSerBeanList);
            if (snapshotPortfolioService.needRun() || jobParam.isForce()) {
                for (PortfolioSerBean portfolioSerBean : portfolioSerBeanList) {
                    String portfolioCode = portfolioSerBean.getPortfolioCode();
                    calcPortfolio(portfolioCode, belongTime);
                }
            }
        } catch (ServiceException e) {
            log.info("[Biz][Portfolio] InformationRatioProcessor fail", e);
        }
        log.info("[Biz][Portfolio] InformationRatioProcessor end");
    }

    @Override
    public void calcPortfolio(String portfolioCode, Date belongTime) throws ServiceException {
        SnapshotPortfolioSerBean snapshotPortfolio = snapshotPortfolioService.getPortfolioByBelongTime(portfolioCode, belongTime);
        try {
            BigDecimal tr = snapshotPortfolio.getTotalReturns();
            BigDecimal T = qryRundays(snapshotPortfolio);
            BigDecimal rm = qryRm(snapshotPortfolio);
            BigDecimal baseReturns = calcBaseReturns(snapshotPortfolio, rm);
            BigDecimal balanceReturns = calcBalanceReturns(tr, baseReturns);
            BigDecimal infoRatio = calcInfoRatio(portfolioCode, belongTime, balanceReturns, T);
            snapshotPortfolio.setBaseReturns(baseReturns);
            snapshotPortfolio.setBaseRealtimeReturns(rm);
            snapshotPortfolio.setBalanceReturns(balanceReturns);
            snapshotPortfolio.setInformationRatio(infoRatio);
            snapshotPortfolio.setErrorFlag(SnapshotPortfolioCode.SUCCESS.getCode());
            snapshotPortfolioService.updateByPrimaryKey(snapshotPortfolio);
        } catch (Exception e) {
            log.error("calc snapshotPortfolio information fail " + snapshotPortfolio);
        }
    }

    public BigDecimal qryRundays(SnapshotPortfolioSerBean snapshotPortfolio) {
        int rundays = 0;
        AidcTradeDateRequest request = new AidcTradeDateRequest();
        request.setBegin_date(snapshotPortfolio.getStartDate().toString());
        request.setEnd_date(QtDateUtils.getCurrDate().toString());
        request.setType(1);
        try {
            rundays = aidcSHHttp.qryTradeDate(request).size();
        } catch (ServiceException e) {
            log.error("qryPortfolioRunDays fail");
        }
        return new BigDecimal(rundays);
    }

    private BigDecimal calcBaseReturns(SnapshotPortfolioSerBean snapshotPortfolio, BigDecimal rm) throws ServiceException {
        //昨天的+现在
        return CalculateUtils.sumBigDecimal(rm, qryHistoryBaseReturns(snapshotPortfolio));
    }

    private BigDecimal calcBalanceReturns(BigDecimal tr, BigDecimal baseReturns) {
        return CalculateUtils.sumBigDecimal(tr, baseReturns);
    }

    private BigDecimal calcInfoRatio(String portfolioCode, Date belongTime, BigDecimal balanceReturns, BigDecimal T) throws ServiceException {
        SnapshotPortfolioSerBean queryStdSP = new SnapshotPortfolioSerBean();
        queryStdSP.setPortfolioCode(portfolioCode);
        queryStdSP.setEndBelongTime(belongTime);
        BigDecimal std = snapshotPortfolioService.getBalanceReturnStdByPortfolioCode(queryStdSP).getStd();
        double result = balanceReturns.doubleValue() / (std.doubleValue() / Math.sqrt(T.doubleValue()) * Math.sqrt(TRANS_DAYS_A_YEAR.doubleValue()));
        return new BigDecimal(result);
    }

    private BigDecimal qryRm(SnapshotPortfolioSerBean snapshotPortfolio) throws ServiceException {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATEFORMAT_YMDHMS);
        String timeStr = sdf.format(snapshotPortfolio.getBelongTime());
        StockEarningRate300Request stockEarningRate300Request = new StockEarningRate300Request();
        stockEarningRate300Request.setDatetime(timeStr);
        StockEarningRate300Response earningResponse = portfolioHttp.queryEarningRate300(stockEarningRate300Request);
        return earningResponse.getData().getData();
    }

    private BigDecimal qryHistoryBaseReturns(SnapshotPortfolioSerBean snapshotPortfolio) throws ServiceException {
        snapshotPortfolio.setErrorFlag("");
        snapshotPortfolio.setEndBelongTime(QtDateUtils.getOpenMarket());
        SnapshotPortfolioSerBean item = snapshotPortfolioService.getLastBeforeOpenMarket(snapshotPortfolio);
        return item.getBaseReturns();
    }
}
