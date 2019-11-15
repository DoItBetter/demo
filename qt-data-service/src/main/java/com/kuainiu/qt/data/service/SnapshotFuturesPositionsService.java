package com.kuainiu.qt.data.service;


import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.SnapshotFuturesPositionsSerBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/13
 * Time: 3:59 PM
 */
public interface SnapshotFuturesPositionsService {
    void batchInsert(List<SnapshotFuturesPositionsSerBean> serBeanList) throws ServiceException;

    List<SnapshotFuturesPositionsSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException;

    BigDecimal getPnl(String assetNo) throws ServiceException;

    SnapshotFuturesPositionsSerBean getLastRecordHistory(String assetNo) throws ServiceException;
}
