package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.dal.entity.SnapshotStkAccount;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.SnapshotStkAccountSerBean;

import java.util.List;

public interface SnapshotStkAccountService {
    List<SnapshotStkAccountSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException;

    long createAndGetId(SnapshotStkAccount snapshotStkAccount) throws ServiceException;
}
