package com.cx.qt.data.service;

import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.dal.entity.SnapshotStkAccount;
import com.cx.qt.data.service.bean.SnapshotStkAccountSerBean;

import java.util.List;

public interface SnapshotStkAccountService {
    List<SnapshotStkAccountSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException;

    long createAndGetId(SnapshotStkAccount snapshotStkAccount) throws ServiceException;
}
