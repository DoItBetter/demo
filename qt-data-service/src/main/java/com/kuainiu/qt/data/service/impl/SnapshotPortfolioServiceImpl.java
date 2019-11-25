package com.kuainiu.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.common.util.CommonConstant;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.dal.dao.SnapshotPortfolioDao;
import com.kuainiu.qt.data.dal.entity.SnapshotPortfolio;
import com.kuainiu.qt.data.dal.entity.SnapshotStkAccount;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.*;
import com.kuainiu.qt.data.service.bean.*;
import com.kuainiu.qt.data.service.code.SnapshotPortfolioCode;
import com.kuainiu.qt.data.service.http.AidcCDHttp;
import com.kuainiu.qt.data.service.http.AidcSHHttp;
import com.kuainiu.qt.data.service.http.request.StockEarningRate300Request;
import com.kuainiu.qt.data.service.http.request.aidc.AidcTradeDateRequest;
import com.kuainiu.qt.data.service.http.response.StockEarningRate300Response;
import com.kuainiu.qt.data.util.BeanUtils;
import com.kuainiu.qt.data.util.SerBeanUtils;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.framework.common.util.CalculateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SnapshotPortfolioServiceImpl implements SnapshotPortfolioService {
    @Autowired
    SnapshotPortfolioDao snapshotPortfolioDao;

    @Autowired
    AidcQryService aidcQryService;

    @Autowired
    SnapshotStkPositionService snapshotStkPositionService;

    @Autowired
    SnapshotFuturesPositionsService snapshotFuturesPositionsService;

    @Autowired
    SnapshotPortfolioCashflowService snapshotPortfolioCashflowService;

    @Autowired
    SnapshotPortfolioService snapshotPortfolioService;

    @Autowired
    SnapshotFuturesAccountService snapshotFuturesAccountService;

    @Autowired
    SnapshotStkAccountService snapshotStkAccountService;

    @Autowired
    SnapshotStkFeeService snapshotStkFeeService;

    @Autowired
    AidcCDHttp portfolioHttp;

    @Autowired
    AidcSHHttp aidcSHHttp;

    private final BigDecimal TRANS_DAYS_A_YEAR = new BigDecimal(252);

    @Override
    public SnapshotPortfolioSerBean getStdByPFCode(String portfolioCode) throws ServiceException {
        SnapshotPortfolioSerBean serBean = new SnapshotPortfolioSerBean();
        try {
            SnapshotPortfolio snapshotPortfolio = snapshotPortfolioDao.getStdByPFCode(portfolioCode);
            BeanMapUtils.map(snapshotPortfolio, serBean);
        } catch (Exception e) {
            log.error("[snapshotPortfolio getStdByPFCode fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR, e);
        }
        return serBean;
    }

    @Override
    public SnapshotPortfolioSerBean getInfoRatioByPFCode(String portfolioCode) throws ServiceException {
        SnapshotPortfolioSerBean serBean = new SnapshotPortfolioSerBean();
        try {
            SnapshotPortfolio portfolio = new SnapshotPortfolio();
            portfolio.setPortfolioCode(portfolioCode);
            portfolio.setErrorFlag(SnapshotPortfolioCode.SUCCESS.getCode());
            SnapshotPortfolio snapshotPortfolio = snapshotPortfolioDao.getInfoRatioByPFCode(portfolio);
            BeanMapUtils.map(snapshotPortfolio, serBean);
        } catch (Exception e) {
            log.error("[snapshotPortfolio getPortfolioInfRatioByPortfolioCode fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR, e);
        }
        return serBean;
    }

    @Override
    public List<SnapshotPortfolioSerBean> findSnapshotPortfolioList(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException {
        List<SnapshotPortfolioSerBean> snapshotPortfolioSerBeanList;
        try {
            SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(reqSerBean);
            List<SnapshotPortfolio> snapshotPortfolioList = snapshotPortfolioDao.findSnapshotPortfolioList(snapshotPortfolio);
            snapshotPortfolioSerBeanList = SerBeanUtils.buildSnapshotPortfolioSerBeanList(snapshotPortfolioList);
        } catch (Exception e) {
            log.error("[snapshotPortfolio findSnapshotPortfolioList fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR, e);
        }
        return snapshotPortfolioSerBeanList;
    }

    @Override
    public List<SnapshotPortfolioSerBean> findLastRecordPerDayByPFCode(String portfolioCode) throws ServiceException {
        List<SnapshotPortfolioSerBean> snapshotPortfolioSerBeanList;
        try {
            List<SnapshotPortfolio> snapshotPortfolioList = snapshotPortfolioDao.findLastRecordPerDayByPFCode(portfolioCode);
            snapshotPortfolioSerBeanList = SerBeanUtils.buildSnapshotPortfolioSerBeanList(snapshotPortfolioList);
        } catch (Exception e) {
            log.error("[snapshotPortfolio findLastRecordPerDayByPFCode fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR, e);
        }
        return snapshotPortfolioSerBeanList;
    }

    @Override
    public boolean needRun() throws ServiceException {
        Date currDate = QtDateUtils.getCurrDate();
        String today = new SimpleDateFormat(CommonConstant.DATE_FORMAT).format(currDate);

        if (!QtDateUtils.isInStockPreparingTime(currDate)) {
            log.info("not in stock trading time");
            return false;
        }
        if (aidcQryService.isTransToday()) {
            return true;
        } else {
            log.info("not in stock trading day");
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setSnapshotPortfolioTx(SnapshotGroupSerBean serBean) throws ServiceException {
        log.info("入库前参数组" + serBean);
        //投资组合快照 添加
        SnapshotPortfolioSerBean portfolioSerBean = serBean.getPortfolioSerBean();
        addOne(portfolioSerBean);

        //股票仓位快照
        List<SnapshotStkPositionSerBean> stkPositionList = serBean.getStkPositionSerBeanList();
        snapshotStkPositionService.batchInsert(stkPositionList);

        //期货仓位快照
        List<SnapshotFuturesPositionsSerBean> futuresPositionsSerBeanList = serBean.getFuturesPositionsSerBeanList();
        snapshotFuturesPositionsService.batchInsert(futuresPositionsSerBeanList);

        //出入金快照
        List<SnapshotPortfolioCashflowSerBean> cashflowSerBeanList = serBean.getPortfolioCashflowSerBeanList();
        snapshotPortfolioCashflowService.batchInsert(cashflowSerBeanList);

        //期货账户快照
        List<SnapshotFuturesAccountSerBean> futuresAccountList = serBean.getFuturesAccountList();
        snapshotFuturesAccountService.batchInsert(futuresAccountList);

        //股票账户快照 添加
        List<SnapshotStkAccountSerBean> stkAccountSerBeanList = serBean.getStkAccountList();
        for (SnapshotStkAccountSerBean snapshotStkAccountSerBean : stkAccountSerBeanList) {
            SnapshotStkAccount snapshotStkAccount = new SnapshotStkAccount();
            BeanMapUtils.map(snapshotStkAccountSerBean, snapshotStkAccount);
            long id = snapshotStkAccountService.createAndGetId(snapshotStkAccount);
            List<SnapshotStkFeeSerBean> stkFeeList = snapshotStkAccountSerBean.getStkFeeList();
            for (SnapshotStkFeeSerBean snapshotStkFeeSerBean : stkFeeList) {
                snapshotStkFeeSerBean.setSnapshotStkAccountId(id);
            }
            snapshotStkFeeService.batchInsert(stkFeeList);
        }
        log.info("入库的stkAccountSerBeanList = {} " , stkAccountSerBeanList);
    }

    @Override
    public SnapshotPortfolioSerBean getBeforeBelongTime(String portfolioCode, Date belongTime) throws ServiceException {
        SnapshotPortfolioSerBean portfolioSerBean = new SnapshotPortfolioSerBean();
        SnapshotPortfolio portfolio = new SnapshotPortfolio();
        try {

            portfolio.setPortfolioCode(portfolioCode);
            portfolio.setEndBelongTime(belongTime);
            portfolio = snapshotPortfolioDao.getLastBeforeOpenMarket(portfolio);
            log.info("[Service][Snapshot]qry last day record, data={}", JSON.toJSONString(portfolio));
            if (null == portfolio || null == portfolio.getId()) {
                return portfolioSerBean;
            }
            BeanMapUtils.map(portfolio, portfolioSerBean);
        } catch (Exception e) {
            log.info("[service][DB][Portfolio] qry last day record from db fail,e", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_PORTFOLIO_QRY);
        }
        return portfolioSerBean;
    }

    @Override
    public void addOne(SnapshotPortfolioSerBean serBean) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
        try {
            snapshotPortfolio = SerBeanUtils.buildSnapshotPortfolio(serBean);
            snapshotPortfolio.setErrorFlag(SnapshotPortfolioCode.UNFINISHED.getCode());
            int rowId = snapshotPortfolioDao.insertSelective(snapshotPortfolio);
            if (rowId <= CommonConstant.ZERO) {
                throw new ServiceException(QtDataRspCode.ERR_DB_ADD);
            }
        } catch (Exception e) {
            log.error("[snapshotPortfolio add fail]" + JSON.toJSONString(snapshotPortfolio), e);
            throw new ServiceException(QtDataRspCode.ERR_DB_ADD, e);
        }
    }

    @Override
    public SnapshotPortfolioSerBean findLastBeforeOpenMarket(String portfolioCode) throws ServiceException {
        SnapshotPortfolioSerBean portfolioSerBean = new SnapshotPortfolioSerBean();
        SnapshotPortfolio portfolio = new SnapshotPortfolio();
        try {

            Date endBelongTime = QtDateUtils.isBeforeOpenMarket() ? QtDateUtils.getOpenMarketYesterday() : QtDateUtils.getOpenMarket();
            portfolio.setPortfolioCode(portfolioCode);
            portfolio.setEndBelongTime(endBelongTime);
            portfolio = snapshotPortfolioDao.getLastBeforeOpenMarket(portfolio);
            log.info("[Service][Snapshot]qry last day record, data={}", JSON.toJSONString(portfolio));
            if (null == portfolio || null == portfolio.getId()) {
                return portfolioSerBean;
            }
            portfolioSerBean = SerBeanUtils.buildSnapshotPortfolio(portfolio);
        } catch (Exception e) {
            log.info("[service][DB][Portfolio] qry last day record from db fail,e", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_PORTFOLIO_QRY);
        }
        return portfolioSerBean;
    }

    @Override
    public void dataCheckAndRepair(Map<String, Object> params, String portfolioCode, Date endBelongTime, int checkMinutes) {
        Date startBelongTime = QtDateUtils.modifyMinutes(endBelongTime, -checkMinutes);
        SnapshotPortfolio tmpParam = getPortfolio(portfolioCode, startBelongTime, endBelongTime, SnapshotPortfolioCode.ERROR.getCode(), CommonConstant.DB_ORDER_ASC, -1);
        List<SnapshotPortfolio> snapshotPortfolioList = snapshotPortfolioDao.getLastPortfolioByPortfolioCodeAndBelongTime(tmpParam);
        for (SnapshotPortfolio snapshotPortfolio : snapshotPortfolioList) {
            Date tmpBelongTime = snapshotPortfolio.getBelongTime();
            log.info("dataCheck and repair ,portfolioCode : {},time:{}", portfolioCode, QtDateUtils.dateFormatSecondStr(tmpBelongTime));
            computePortfolioInformationRatio(params, portfolioCode, tmpBelongTime, null);
        }
    }

    private SnapshotPortfolio getPortfolio(String portfolioCode, Date startBelongTime, Date endBelongTime, String errorFlag, String order, int limit) {
        SnapshotPortfolio param = new SnapshotPortfolio();
        param.setPortfolioCode(portfolioCode);
        if (startBelongTime != null) {
            param.setStartBelongTime(startBelongTime);
        }
        if (endBelongTime != null) {
            param.setEndBelongTime(endBelongTime);
        }
        if (StringUtils.isNotBlank(errorFlag)) {
            param.setErrorFlag(errorFlag);
        }
        if (CommonConstant.DB_ORDER_DESC.equals(order)) {
            param.setOrder(CommonConstant.DB_ORDER_DESC);
        } else {
            param.setOrder(CommonConstant.DB_ORDER_ASC);
        }
        if (limit > 0) {
            param.setLimit(limit);
        }
        return param;
    }

    @Override
    public void computePortfolioInformationRatio(Map<String, Object> params, String portfolioCode, Date belongTime, String errorFlag) {
        try {
            log.info("start computePortfolioInformationRatio,belongTime:{}", belongTime);
            SnapshotPortfolio param = new SnapshotPortfolio();
            param.setBelongTime(belongTime);
            param.setPortfolioCode(portfolioCode);
            SnapshotPortfolio snapshotPortfolio = snapshotPortfolioDao.getPortfolioByPortfolioCodeAndBelongTime(param);
            if (snapshotPortfolio == null) {
                log.warn("portfolio snapshot data do not exists!" + portfolioCode + ",time:" + QtDateUtils.dateFormatSecondStr(belongTime));
                return;
            }
            BigDecimal tr = snapshotPortfolio.getTotalReturns().add(snapshotPortfolio.getRealtimeReturns());
            BigDecimal T = new BigDecimal(((PortfolioRunInfoBean) params.get(portfolioCode)).getRunDays());
            BigDecimal totalRealtimeReturns = CalculateUtils.multiply(CalculateUtils.divide(tr, T), TRANS_DAYS_A_YEAR);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATEFORMAT_YMDHMS);
            String timeStr = sdf.format(belongTime);
            StockEarningRate300Request stockEarningRate300Request = new StockEarningRate300Request();
            stockEarningRate300Request.setDatetime(timeStr);
            StockEarningRate300Response earningResponse = portfolioHttp.queryEarningRate300(stockEarningRate300Request);
            if (!timeStr.equals(earningResponse.getData().getTimestamp()) && !CommonConstant.STOCK_DAY_PM_STARTTIME.equals(timeStr.split(" ")[1])) {
                errorFlag = SnapshotPortfolioCode.ERROR.getCode();
            }
            BigDecimal baseReturns = earningResponse.getData().getData().add((BigDecimal) ((PortfolioRunInfoBean) params.get(portfolioCode)).getBaseTotalReturns());
            BigDecimal balanceReturns = CalculateUtils.subtract(totalRealtimeReturns, baseReturns);
            long id = snapshotPortfolio.getId();
            snapshotPortfolio = new SnapshotPortfolio();
            snapshotPortfolio.setId(id);
            snapshotPortfolio.setTotalRealtimeReturns(totalRealtimeReturns);
            snapshotPortfolio.setBaseReturns(baseReturns);
            snapshotPortfolio.setBaseRealtimeReturns(earningResponse.getData().getData());
            snapshotPortfolio.setBalanceReturns(balanceReturns);
            if (StringUtils.isEmpty(errorFlag)) {
                snapshotPortfolio.setErrorFlag(SnapshotPortfolioCode.SUCCESS.getCode());
            } else {
                snapshotPortfolio.setErrorFlag(errorFlag);
            }
            snapshotPortfolioDao.updateByPrimaryKeySelective(snapshotPortfolio);
            SnapshotPortfolio queryStdSP = new SnapshotPortfolio();
            queryStdSP.setPortfolioCode(portfolioCode);
            queryStdSP.setEndBelongTime(belongTime);
            BigDecimal std = snapshotPortfolioDao.getBalanceReturnStdByPortfolioCode(queryStdSP).getStd();
            double result = balanceReturns.doubleValue() / (std.doubleValue() / Math.sqrt(T.doubleValue()) * Math.sqrt(TRANS_DAYS_A_YEAR.doubleValue()));
            snapshotPortfolio.setInformationRatio(new BigDecimal(result));
            snapshotPortfolioDao.updateByPrimaryKeySelective(snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshotPortfolio failed,portfolioCode:" + portfolioCode + ",time:" + QtDateUtils.dateFormatSecondStr(belongTime), e);
            saveLastPortfolioInformationRatio(params, portfolioCode, belongTime);
        }
    }

    @Override
    public SnapshotPortfolioSerBean getPortfolioByBelongTime(String portfolioCode, Date belongTime) {
        SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
        try {
            snapshotPortfolio = snapshotPortfolioDao.getPortfolioByPortfolioCodeAndBelongTime(getPortfolio(portfolioCode, belongTime));
        } catch (Exception e) {
            log.error("get snapshotPortfolio fail " + snapshotPortfolio, e);
        }
        return SerBeanUtils.buildSnapshotPortfolioSerBean(snapshotPortfolio);
    }

    @Override
    public void calcPortfolio(String portfolioCode, Date belongTime) {
        SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
        try {
            snapshotPortfolio = snapshotPortfolioDao.getPortfolioByPortfolioCodeAndBelongTime(getPortfolio(portfolioCode, belongTime));
            if (snapshotPortfolio == null) {
                log.warn("portfolio snapshot data do not exists!" + portfolioCode + ",time:" + belongTime);
                throw new ServiceException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE);
            }
            BigDecimal tr = CalculateUtils.sumBigDecimal(snapshotPortfolio.getTotalReturns(), snapshotPortfolio.getRealtimeReturns());
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
            snapshotPortfolioDao.updateByPrimaryKeySelective(snapshotPortfolio);
        } catch (Exception e) {
            log.error("get snapshotPortfolio fail " + snapshotPortfolio);
        }
    }

    public BigDecimal qryRundays(SnapshotPortfolio snapshotPortfolio) {
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

    private BigDecimal calcBaseReturns(SnapshotPortfolio snapshotPortfolio, BigDecimal rm) {
        //昨天的+现在
        return CalculateUtils.sumBigDecimal(rm, qryHistoryBaseReturns(snapshotPortfolio));
    }

    private BigDecimal calcBalanceReturns(BigDecimal tr, BigDecimal baseReturns) {
        return CalculateUtils.sumBigDecimal(tr, baseReturns);
    }

    private BigDecimal calcInfoRatio(String portfolioCode, Date belongTime, BigDecimal balanceReturns, BigDecimal T) {
        SnapshotPortfolio queryStdSP = new SnapshotPortfolio();
        queryStdSP.setPortfolioCode(portfolioCode);
        queryStdSP.setEndBelongTime(belongTime);
        BigDecimal std = snapshotPortfolioDao.getBalanceReturnStdByPortfolioCode(queryStdSP).getStd();
        double result = balanceReturns.doubleValue() / (std.doubleValue() / Math.sqrt(T.doubleValue()) * Math.sqrt(TRANS_DAYS_A_YEAR.doubleValue()));
        return new BigDecimal(result);
    }

    private BigDecimal qryRm(SnapshotPortfolio snapshotPortfolio) throws ServiceException {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATEFORMAT_YMDHMS);
        String timeStr = sdf.format(snapshotPortfolio.getBelongTime());
        StockEarningRate300Request stockEarningRate300Request = new StockEarningRate300Request();
        stockEarningRate300Request.setDatetime(timeStr);
        StockEarningRate300Response earningResponse = portfolioHttp.queryEarningRate300(stockEarningRate300Request);
        return earningResponse.getData().getData();
    }

    private BigDecimal qryHistoryBaseReturns(SnapshotPortfolio snapshotPortfolio) {
        snapshotPortfolio.setErrorFlag("");
        snapshotPortfolio.setEndBelongTime(QtDateUtils.getOpenMarket());
        SnapshotPortfolio item = snapshotPortfolioDao.getLastBeforeOpenMarket(snapshotPortfolio);
        return item.getBaseReturns();
    }

    public void saveLastPortfolioInformationRatio(Map<String, Object> params, String portfolioCode, Date belongTime) {
        try {
            SnapshotPortfolio param = getPortfolio(portfolioCode, belongTime);
            SnapshotPortfolio snapshotPortfolio = snapshotPortfolioDao.getPortfolioByPortfolioCodeAndBelongTime(param);
            belongTime = QtDateUtils.subtractOneMinute(belongTime);
            SnapshotPortfolio tmpParam = getPortfolio(portfolioCode, null, belongTime, null, CommonConstant.DB_ORDER_DESC, 1);
            List<SnapshotPortfolio> snapshotPortfolioList = snapshotPortfolioDao.getLastPortfolioByPortfolioCodeAndBelongTime(param);
            SnapshotPortfolio lastSnapshotPortfolio = snapshotPortfolioList.get(0);
            long id = snapshotPortfolio.getId();
            snapshotPortfolio = new SnapshotPortfolio();
            snapshotPortfolio.setId(id);
            snapshotPortfolio.setTotalRealtimeReturns(lastSnapshotPortfolio.getTotalRealtimeReturns());
            snapshotPortfolio.setBaseReturns(lastSnapshotPortfolio.getBaseReturns());
            snapshotPortfolio.setBaseRealtimeReturns(lastSnapshotPortfolio.getBaseRealtimeReturns());
            snapshotPortfolio.setBalanceReturns(lastSnapshotPortfolio.getBalanceReturns());
            snapshotPortfolio.setInformationRatio(lastSnapshotPortfolio.getInformationRatio());
            snapshotPortfolio.setErrorFlag(SnapshotPortfolioCode.ERROR.getCode());
            snapshotPortfolioDao.updateByPrimaryKeySelective(snapshotPortfolio);
        } catch (Exception e) {
            log.error("saveLastPortfolioInformationRatio failed,portfolioCode:" + portfolioCode + ",time:" + QtDateUtils.dateFormatSecondStr(belongTime), e);
        }
    }

    private SnapshotPortfolio getPortfolio(String portfolioCode, Date belongTime) {
        SnapshotPortfolio param = new SnapshotPortfolio();
        param.setPortfolioCode(portfolioCode);
        param.setBelongTime(belongTime);
        return param;
    }
}
