package com.kuainiu.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.dal.dao.SnapshotPortfolioDao;
import com.kuainiu.qt.data.dal.entity.SnapshotPortfolio;
import com.kuainiu.qt.data.dal.entity.SnapshotStkAccount;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.*;
import com.kuainiu.qt.data.service.bean.*;
import com.kuainiu.qt.data.service.code.SnapshotPortfolioCode;
import com.kuainiu.qt.data.util.BeanUtils;
import com.kuainiu.qt.data.util.SerBeanUtils;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

//    @Autowired
//    SysSeqNoManager sysSeqNoManager;

    @Autowired
    SnapshotPortfolioService snapshotPortfolioService;

    @Autowired
    SnapshotFuturesAccountService snapshotFuturesAccountService;

    @Autowired
    SnapshotStkAccountService snapshotStkAccountService;

    @Autowired
    SnapshotStkFeeService snapshotStkFeeService;

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
        log.info("入库前", stkAccountSerBeanList);
    }

    @Override
    public SnapshotPortfolioSerBean getBeforeBelongTime(String portfolioCode, Date belongTime) throws ServiceException {
        SnapshotPortfolioSerBean portfolioSerBean = new SnapshotPortfolioSerBean();
        SnapshotPortfolio portfolio = new SnapshotPortfolio();
        try {

            portfolio.setPortfolioCode(portfolioCode);
            portfolio.setEndBelongTime(belongTime);
            log.info("[Service][Snapshot]qry last day record, qryData={}");
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
            log.info("[Service][Snapshot]qry last day record, qryData={}");
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
}
