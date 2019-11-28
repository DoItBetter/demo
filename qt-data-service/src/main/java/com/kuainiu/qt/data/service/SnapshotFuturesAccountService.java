package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.SnapshotFuturesAccountSerBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/28
 * Time: 9:17 AM
 */
public interface SnapshotFuturesAccountService {
    void addOne(SnapshotFuturesAccountSerBean serBean) throws ServiceException;

    void batchInsert(List<SnapshotFuturesAccountSerBean> serBeanList) throws ServiceException;

    SnapshotFuturesAccountSerBean getLastBeforeOpenMarket(String accountCode) throws ServiceException;

    List<SnapshotFuturesAccountSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException;
}
