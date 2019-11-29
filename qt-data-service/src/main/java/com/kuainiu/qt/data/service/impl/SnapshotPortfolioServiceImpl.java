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
import com.kuainiu.qt.data.service.http.AidcSHHttp;
import com.kuainiu.qt.data.util.BeanUtils;
import com.kuainiu.qt.data.util.SerBeanUtils;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
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
    StkPositionService stkPositionService;

    @Autowired
    FuturesPositionsService futuresPositionsService;

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
    AidcSHHttp aidcSHHttp;



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
        stkPositionService.batchInsert(stkPositionList);

        //期货仓位快照
        List<FuturesPositionSerBean> futuresPositionSerBeanList = serBean.getFuturesPositionSerBeanList();
        futuresPositionsService.batchInsert(futuresPositionSerBeanList);

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
            portfolio = snapshotPortfolioDao.findByBelongTimeAndErrorFlag(portfolio);
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
    public SnapshotPortfolioSerBean findByBelongTimeAndErrorFlag(SnapshotPortfolioReqSerBean snapshotPortfolioReqSerBean) throws ServiceException {
        SnapshotPortfolioSerBean portfolioSerBean = new SnapshotPortfolioSerBean();
        SnapshotPortfolio portfolio = BeanUtils.buildSnapshotPortfolio(snapshotPortfolioReqSerBean);
        try {
            log.info("[Service][Snapshot]findByBelongTimeAndErrorFlag, db request={}", JSON.toJSONString(portfolio));
            portfolio = snapshotPortfolioDao.findByBelongTimeAndErrorFlag(portfolio);
            log.info("[Service][Snapshot]findByBelongTimeAndErrorFlag, db response={}", JSON.toJSONString(portfolio));
            if (null == portfolio || null == portfolio.getId()) {
                return portfolioSerBean;
            }
            portfolioSerBean = SerBeanUtils.buildSnapshotPortfolio(portfolio);
        } catch (Exception e) {
            log.info("[service][DB][Portfolio] findByBelongTimeAndErrorFlag from db fail,e", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_PORTFOLIO_QRY);
        }
        return portfolioSerBean;
    }

    @Override
    public SnapshotPortfolioSerBean getPortfolioByBelongTime(String portfolioCode, Date belongTime) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = getPortfolio(portfolioCode, belongTime, SnapshotPortfolioCode.UNFINISHED.getCode());
        try {
            snapshotPortfolio = snapshotPortfolioDao.getPortfolioByPortfolioCodeAndBelongTime(snapshotPortfolio);
            log.info("getPortfolioByPortfolioCodeAndBelongTime , snapshotPortfolio res = {}", snapshotPortfolio);
        } catch (Exception e) {
            log.error("get snapshotPortfolio fail " + snapshotPortfolio, e);
        }
        if (snapshotPortfolio == null) {
            log.error("getPortfolioByBelongTime snapshotPortfolio is null, belongTime = {}", belongTime);
        }
        return SerBeanUtils.buildSnapshotPortfolioSerBean(snapshotPortfolio);
    }

    private SnapshotPortfolio getPortfolio(String portfolioCode, Date belongTime, String errorFlag) {
        SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
        snapshotPortfolio.setPortfolioCode(portfolioCode);
        snapshotPortfolio.setBelongTime(belongTime);
        snapshotPortfolio.setErrorFlag(errorFlag);
        return snapshotPortfolio;
    }

    @Override
    public SnapshotPortfolioSerBean findOneOneMinuteAgo(String portfolioCode) throws ServiceException {
        SnapshotPortfolioSerBean serBean = new SnapshotPortfolioSerBean();
        try {
            //为了统一列表
            SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
            snapshotPortfolio.setPortfolioCode(portfolioCode);
            snapshotPortfolio.setEndBelongTime(QtDateUtils.getMinutesAgo(1));
            log.info("find portfolio one minute ago,snapshotPortfolio={}",JSON.toJSONString(snapshotPortfolio));
            snapshotPortfolio = snapshotPortfolioDao.findOneByPortfolioCode(snapshotPortfolio);
            if (null != snapshotPortfolio) {
                BeanMapUtils.map(snapshotPortfolio, serBean);
            }
        } catch (Exception e) {
            log.error("[snapshotPortfolio findOneByPortfolioCode fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR, e);
        }
        return serBean;
    }

    @Override
    public SnapshotPortfolioSerBean getByBelongTimeAndErrorFlag(SnapshotPortfolioSerBean snapshotPortfolioSerBean) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(snapshotPortfolioSerBean);
        try {
            snapshotPortfolio = snapshotPortfolioDao.findByBelongTimeAndErrorFlag(snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshot portfolio findByBelongTimeAndErrorFlag error = {}", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR);
        }
        return SerBeanUtils.buildSnapshotPortfolioSerBean(snapshotPortfolio);
    }

    @Override
    public SnapshotPortfolioSerBean getBalanceReturnStdByPortfolioCode(SnapshotPortfolioSerBean snapshotPortfolioSerBean) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(snapshotPortfolioSerBean);
        try {
            snapshotPortfolio = snapshotPortfolioDao.getBalanceReturnStdByPortfolioCode(snapshotPortfolio);
            log.info("snapshot portfolio getBalanceReturnStdByPortfolioCode db response={}", snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshot portfolio getBalanceReturnStdByPortfolioCode error = {}", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR);
        }
        return SerBeanUtils.buildSnapshotPortfolioSerBean(snapshotPortfolio);
    }

    @Override
    public void updateReturnsFields(SnapshotPortfolioSerBean snapshotPortfolioSerBean) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(snapshotPortfolioSerBean);
        int i = 0;
        try {
            i = snapshotPortfolioDao.updateReturnsFields(snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshot portfolio updateReturnsFields error = {}", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR);
        }
        if (i == 0) {
            log.error("snapshot portfolio updateReturnsFields fail");
            throw new ServiceException(QtDataRspCode.ERR_DB_UPDATE);
        }
    }

    @Override
    public void updateInfoRatio(SnapshotPortfolioSerBean snapshotPortfolioSerBean) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(snapshotPortfolioSerBean);
        int i = 0;
        try {
            i = snapshotPortfolioDao.updateInfoRatio(snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshot portfolio updateInfoRatio error = {}", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR);
        }
        if (i == 0) {
            log.error("snapshot portfolio updateInfoRatio fail");
            throw new ServiceException(QtDataRspCode.ERR_DB_UPDATE);
        }
    }
}
