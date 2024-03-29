package com.cx.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.service.*;
import com.cx.qt.data.service.bean.*;
import com.cx.qt.data.util.BeanUtils;
import com.cx.qt.data.util.SerBeanUtils;
import com.cx.qt.data.common.util.CommonConstant;
import com.cx.qt.data.common.util.QtDateUtils;
import com.cx.qt.data.dal.dao.SnapshotPortfolioDao;
import com.cx.qt.data.dal.entity.SnapshotPortfolio;
import com.cx.qt.data.dal.entity.SnapshotStkAccount;
import com.cx.qt.data.facade.code.QtDataRspCode;
import com.cx.qt.data.service.*;
import com.cx.qt.data.service.bean.*;
import com.cx.qt.data.service.code.SnapshotPortfolioCode;
import com.cx.qt.data.service.http.AidcSHHttp;
import com.cx.qt.framework.common.util.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
            if (snapshotPortfolio != null) {
                serBean = SerBeanUtils.buildSnapshotPortfolioSerBean(snapshotPortfolio);
            }
        } catch (Exception e) {
            log.error("[snapshotPortfolio getStdByPFCode fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR, e);
        }
        return serBean;
    }

    @Override
    public SnapshotPortfolioSerBean getByPFCodeAndErrorFlag(String portfolioCode) throws ServiceException {
        SnapshotPortfolioSerBean serBean = new SnapshotPortfolioSerBean();
        try {
            SnapshotPortfolio portfolio = new SnapshotPortfolio();
            portfolio.setPortfolioCode(portfolioCode);
            portfolio.setErrorFlag(SnapshotPortfolioCode.SUCCESS.getCode());
            SnapshotPortfolio snapshotPortfolio = snapshotPortfolioDao.getByPFCodeAndErrorFlag(portfolio);
            if (snapshotPortfolio != null) {
                serBean = SerBeanUtils.buildSnapshotPortfolioSerBean(snapshotPortfolio);
            }
        } catch (Exception e) {
            log.error("[snapshotPortfolio getPortfolioInfRatioByPortfolioCode fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR, e);
        }
        return serBean;
    }

    @Override
    public List<SnapshotPortfolioSerBean> findSnapshotPortfolioList(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException {
        List<SnapshotPortfolioSerBean> snapshotPortfolioSerBeanList = new ArrayList<>();
        try {
            SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(reqSerBean);
            List<SnapshotPortfolio> snapshotPortfolioList = snapshotPortfolioDao.findSnapshotPortfolioList(snapshotPortfolio);
            if (snapshotPortfolioList.size() != CommonConstant.ZERO) {
                snapshotPortfolioSerBeanList = SerBeanUtils.buildSnapshotPortfolioSerBeanList(snapshotPortfolioList);
            }
        } catch (Exception e) {
            log.error("[snapshotPortfolio findSnapshotPortfolioList fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR, e);
        }
        return snapshotPortfolioSerBeanList;
    }

    @Override
    public List<SnapshotPortfolioSerBean> findLastRecordPerDayByPFCode(String portfolioCode) throws ServiceException {
        List<SnapshotPortfolioSerBean> snapshotPortfolioSerBeanList = new ArrayList<>();
        try {
            List<SnapshotPortfolio> snapshotPortfolioList = snapshotPortfolioDao.findLastRecordPerDayByPFCode(portfolioCode);
            if (snapshotPortfolioList.size() != CommonConstant.ZERO) {
                snapshotPortfolioSerBeanList = SerBeanUtils.buildSnapshotPortfolioSerBeanList(snapshotPortfolioList);
            }
        } catch (Exception e) {
            log.error("[snapshotPortfolio findLastRecordPerDayByPFCode fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR, e);
        }
        return snapshotPortfolioSerBeanList;
    }

    @Override
    public boolean needRun() throws ServiceException {
        Date currDate = QtDateUtils.getCurrDate();

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
            portfolio = snapshotPortfolioDao.findByPFCodeBelongTimeAndErrorFlag(portfolio);
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
    public SnapshotPortfolioSerBean findByPFCodeBelongTimeAndErrorFlag(SnapshotPortfolioReqSerBean snapshotPortfolioReqSerBean) throws ServiceException {
        SnapshotPortfolioSerBean portfolioSerBean = new SnapshotPortfolioSerBean();
        SnapshotPortfolio portfolio = BeanUtils.buildSnapshotPortfolio(snapshotPortfolioReqSerBean);
        try {
            log.info("[Service][Snapshot]findByPFCodeBelongTimeAndErrorFlag, db request={}", JSON.toJSONString(portfolio));
            portfolio = snapshotPortfolioDao.findByPFCodeBelongTimeAndErrorFlag(portfolio);
            log.info("[Service][Snapshot]findByPFCodeBelongTimeAndErrorFlag, db response={}", JSON.toJSONString(portfolio));
            if (null == portfolio || null == portfolio.getId()) {
                return portfolioSerBean;
            }
            portfolioSerBean = SerBeanUtils.buildSnapshotPortfolio(portfolio);
        } catch (Exception e) {
            log.info("[service][DB][Portfolio] findByPFCodeBelongTimeAndErrorFlag from db fail,e", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_PORTFOLIO_QRY);
        }
        return portfolioSerBean;
    }

    @Override
    public SnapshotPortfolioSerBean getPortfolioByBelongTime(String portfolioCode, Date belongTime, String errorFlag) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = getPortfolio(portfolioCode, belongTime, errorFlag);
        try {
            snapshotPortfolio = snapshotPortfolioDao.getPortfolioByPortfolioCodeAndBelongTime(snapshotPortfolio);
            log.info("getPortfolioByPortfolioCodeAndBelongTime , snapshotPortfolio res = {}", snapshotPortfolio);
        } catch (Exception e) {
            log.error("get snapshotPortfolio fail " + snapshotPortfolio, e);
        }
        if (snapshotPortfolio == null) {
            log.error("getPortfolioByBelongTime snapshotPortfolio is null, portfolioCode = {}, belongTime = {}, errorflag = {}", portfolioCode, belongTime, errorFlag);
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
    public SnapshotPortfolioSerBean getByPFCodeBelongTimeAndErrorFlag(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException {
        SnapshotPortfolioSerBean serBean = new SnapshotPortfolioSerBean();
        SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(reqSerBean);
        try {
            snapshotPortfolio = snapshotPortfolioDao.findByPFCodeBelongTimeAndErrorFlag(snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshot portfolio findByPFCodeBelongTimeAndErrorFlag error = {}", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR);
        }
        if (snapshotPortfolio != null) {
            serBean = SerBeanUtils.buildSnapshotPortfolioSerBean(snapshotPortfolio);
        }
        return serBean;
    }

    @Override
    public SnapshotPortfolioSerBean getBalanceReturnStdByPortfolioCode(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException {
        SnapshotPortfolioSerBean serBean = new SnapshotPortfolioSerBean();
        SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(reqSerBean);
        try {
            snapshotPortfolio = snapshotPortfolioDao.getBalanceReturnStdByPortfolioCode(snapshotPortfolio);
            log.info("snapshot portfolio getBalanceReturnStdByPortfolioCode db response={}", snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshot portfolio getBalanceReturnStdByPortfolioCode error = {}", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR);
        }
        if (snapshotPortfolio != null) {
            serBean = SerBeanUtils.buildSnapshotPortfolioSerBean(snapshotPortfolio);
        }
        return serBean;
    }

    @Override
    public void updateReturnsFields(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(reqSerBean);
        int i = CommonConstant.ZERO;
        try {
            i = snapshotPortfolioDao.updateReturnsFields(snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshot portfolio updateReturnsFields error = {}", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR);
        }
        if (i == CommonConstant.ZERO) {
            log.error("snapshot portfolio updateReturnsFields fail");
            throw new ServiceException(QtDataRspCode.ERR_DB_UPDATE);
        }
    }

    @Override
    public void updateInfoRatio(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(reqSerBean);
        int i = CommonConstant.ZERO;
        try {
            i = snapshotPortfolioDao.updateInfoRatio(snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshot portfolio updateInfoRatio error = {}", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR);
        }
        if (i == CommonConstant.ZERO) {
            log.error("snapshot portfolio updateInfoRatio fail");
            throw new ServiceException(QtDataRspCode.ERR_DB_UPDATE);
        }
    }

    @Override
    public SnapshotPortfolioSerBean findByBelongTimeAndErrorFlag(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException {
        SnapshotPortfolioSerBean serBean = new SnapshotPortfolioSerBean();
        SnapshotPortfolio snapshotPortfolio = BeanUtils.buildSnapshotPortfolio(reqSerBean);
        try {
            snapshotPortfolio = snapshotPortfolioDao.findByBelongTimeAndErrorFlag(snapshotPortfolio);
            log.info("snapshot portfolio findByPFCodeBelongTimeAndErrorFlag db response={}", snapshotPortfolio);
        } catch (Exception e) {
            log.error("snapshot portfolio findByPFCodeBelongTimeAndErrorFlag error = {}", e);
            throw new ServiceException(QtDataRspCode.ERR_DBERR);
        }
        if (snapshotPortfolio != null) {
            serBean = SerBeanUtils.buildSnapshotPortfolioSerBean(snapshotPortfolio);
        }
        return serBean;
    }
}
