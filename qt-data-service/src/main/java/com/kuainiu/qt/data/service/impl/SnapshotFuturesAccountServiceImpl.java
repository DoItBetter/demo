package com.kuainiu.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.dal.dao.SnapshotFuturesAccountDao;
import com.kuainiu.qt.data.dal.entity.SnapshotFuturesAccount;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.SnapshotFuturesAccountService;
import com.kuainiu.qt.data.service.bean.SnapshotFuturesAccountSerBean;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/28
 * Time: 9:19 AM
 */
@Service
@Slf4j
public class SnapshotFuturesAccountServiceImpl implements SnapshotFuturesAccountService {

    @Autowired
    SnapshotFuturesAccountDao futuresAccountDao;

    @Override
    public void addOne(SnapshotFuturesAccountSerBean serBean) throws ServiceException {
        if (serBean == null) {
            throw new ServiceException(QtDataRspCode.ERR_SNAPSHOT_FUTURES_ACCOUNT_SER_BEAN);
        }
        try {
            SnapshotFuturesAccount futuresAccount = new SnapshotFuturesAccount();
            BeanMapUtils.map(serBean, futuresAccount);
            futuresAccountDao.insertSelective(futuresAccount);
        } catch (Exception e) {
            log.error("[Service][Snapshot]add account fail,e", e);
            log.error("[Service][Snapshot]add account fail,serBean={}", JSON.toJSONString(serBean));
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_ADD);
        }
    }

    @Override
    public void batchInsert(List<SnapshotFuturesAccountSerBean> serBeanList) throws ServiceException {
        if (null == serBeanList || serBeanList.size() == CommonConstant.ZERO) {
            log.warn("[batch insert SnapshotFuturesAccount empty]");
            return;
        }
        try {
            List<SnapshotFuturesAccount> futuresAccountList = BeanMapUtils.mapAsList(serBeanList, SnapshotFuturesAccount.class);
            futuresAccountDao.batchInsert(futuresAccountList);
            log.info("批量入库的futuresAccountList = {} " , futuresAccountList);
        } catch (Exception e) {
            log.error("[Service][Snapshot]batch insert SnapshotFuturesAccount fail", e);
            log.error("[Service][Snapshot]batch insert SnapshotFuturesAccount fail,data={}", JSON.toJSONString(serBeanList));
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_BATCH_ADD);
        }
    }

    @Override
    public SnapshotFuturesAccountSerBean getLastBeforeOpenMarket(String accountCode) throws ServiceException {
        if (StringUtils.isBlank(accountCode)) {
            throw new ServiceException(QtDataRspCode.ERR_SNAPSHOT_FUTURES_ACCOUNT_CODE);
        }
        SnapshotFuturesAccountSerBean serBean = new SnapshotFuturesAccountSerBean();
        SnapshotFuturesAccount futuresAccount = new SnapshotFuturesAccount();
        try {
            Date endBelongTime = QtDateUtils.isBeforeOpenMarket() ? QtDateUtils.getOpenMarketYesterday() : QtDateUtils.getOpenMarket();
            futuresAccount.setAccountCode(accountCode);
            futuresAccount.setEndBelongTime(endBelongTime);
            futuresAccount = futuresAccountDao.getLastBeforeOpenMarket(futuresAccount);
            if (null != futuresAccount) {
                BeanMapUtils.map(futuresAccount, serBean);
            }
        } catch (Exception e) {
            log.error("[Service][Snapshot]qry last account before market fail,e", e);
            log.error("[Service][Snapshot]qry last account before market fail,data={}", JSON.toJSONString(futuresAccount));
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_QRY);
        }
        return serBean;
    }

    @Override
    public List<SnapshotFuturesAccountSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException {
        if (StringUtils.isBlank(snapshotCode)) {
            throw new ServiceException(QtDataRspCode.ERR_SNAPSHOT_FUTURES_ACCOUNT_SNAPSHOT_CODE);
        }
        log.warn("[Service][Snapshot]qry futures account by snapshotcode,snapshotCode={}", snapshotCode);
        List<SnapshotFuturesAccountSerBean> accountList = new ArrayList<>();
        SnapshotFuturesAccount futuresAccount = new SnapshotFuturesAccount();
        try {
            futuresAccount.setSnapshotCode(snapshotCode);
            List<SnapshotFuturesAccount> accountListDB = futuresAccountDao.getListBySnapshotCode(futuresAccount);
            log.warn("[Service][Snapshot]qry futures account by snapshotcode,res={}", JSON.toJSONString(accountListDB));
            if (null == accountListDB) {
                log.warn("[Service][Snapshot]qry futures account list empty,snapshotCode={}", JSON.toJSONString(accountListDB));
            } else {
                accountList = BeanMapUtils.mapAsList(accountListDB, SnapshotFuturesAccountSerBean.class);
            }
        } catch (Exception e) {
            log.error("[Service][Snapshot]qry last account list fail,e", e);
            log.error("[Service][Snapshot]qry last account list fail,data={}", JSON.toJSONString(futuresAccount));
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_LIST_QRY);
        }
        return accountList;
    }

    @Override
    public void update(SnapshotFuturesAccountSerBean serBean) throws ServiceException {
        SnapshotFuturesAccount account = new SnapshotFuturesAccount();
        BeanMapUtils.map(serBean, account);
        futuresAccountDao.updateByPrimaryKey(account);
    }

    @Override
    public SnapshotFuturesAccountSerBean getLastBeforeOpenMarket(SnapshotFuturesAccountSerBean serBean) throws ServiceException {
        if (StringUtils.isBlank(serBean.getAccountCode())) {
            throw new ServiceException(QtDataRspCode.ERR_SNAPSHOT_FUTURES_ACCOUNT_CODE);
        }
        log.info("getLastBeforeOpenMarket,serBean={}", serBean);
        SnapshotFuturesAccount futuresAccount = new SnapshotFuturesAccount();
        try {
            BeanMapUtils.map(serBean, futuresAccount);
            futuresAccount = futuresAccountDao.getLastBeforeOpenMarket(futuresAccount);
            if (null != futuresAccount) {
                BeanMapUtils.map(futuresAccount, serBean);
            }
        } catch (Exception e) {
            log.error("[Service][Snapshot]qry last account before market fail,e", e);
            log.error("[Service][Snapshot]qry last account before market fail,data={}", JSON.toJSONString(futuresAccount));
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_QRY);
        }
        return serBean;
    }
}
