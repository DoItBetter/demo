package com.cx.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.service.SnapshotStkAccountService;
import com.cx.qt.data.dal.dao.SnapshotStkAccountDao;
import com.cx.qt.data.dal.entity.SnapshotStkAccount;
import com.cx.qt.data.facade.code.QtDataRspCode;
import com.cx.qt.data.service.bean.SnapshotStkAccountSerBean;
import com.cx.qt.framework.common.util.BeanMapUtils;
import com.cx.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SnapshotStkAccountServiceImpl implements SnapshotStkAccountService {
    @Autowired
    SnapshotStkAccountDao stkAccountDao;

    @Override
    public List<SnapshotStkAccountSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException {
        if (StringUtils.isBlank(snapshotCode)) {
            throw new ServiceException(QtDataRspCode.ERR_SNAPSHOT_FUTURES_ACCOUNT_SNAPSHOT_CODE);
        }
        log.warn("[Service][Snapshot]qry stk account by snapshotcode,snapshotCode={}", snapshotCode);
        List<SnapshotStkAccountSerBean> accountList = new ArrayList<>();
        SnapshotStkAccount stkAccount = new SnapshotStkAccount();
        try {
            stkAccount.setSnapshotCode(snapshotCode);
            List<SnapshotStkAccount> accountListDB = stkAccountDao.getListBySnapshotCode(stkAccount);
            log.warn("[Service][Snapshot]qry stk account by snapshotcode,res={}", JSON.toJSONString(accountListDB));
            if (null == accountListDB) {
                log.warn("[Service][Snapshot]qry stk account list empty,snapshotCode={}", JSON.toJSONString(accountListDB));
            } else {
                accountList = BeanMapUtils.mapAsList(accountListDB, SnapshotStkAccountSerBean.class);
            }
        } catch (Exception e) {
            log.error("[Service][Snapshot]qry last account list fail,e", e);
            log.error("[Service][Snapshot]qry last account list fail,data={}", JSON.toJSONString(stkAccount));
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_ACCOUNT_LIST_QRY);
        }
        return accountList;
    }

    @Override
    public long createAndGetId(SnapshotStkAccount snapshotStkAccount) throws ServiceException {
        long id = 0;
        if (null == snapshotStkAccount) {
            log.warn("[SnapshotStkAccount empty]");
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_STK_ACCOUNT_ADD);
        }
        try {
            int i = stkAccountDao.createAndGetId(snapshotStkAccount);
            if (i == CommonConstant.INT_ONE) {
                id = snapshotStkAccount.getId();
            }
        } catch (Exception e) {
            log.error("[Service][Snapshot]insert SnapshotStkAccount fail", e);
            log.error("[Service][Snapshot]insert SnapshotStkAccount fail,data={}", JSON.toJSONString(snapshotStkAccount));
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_STK_ACCOUNT_ADD);
        }
        return id;
    }
}
