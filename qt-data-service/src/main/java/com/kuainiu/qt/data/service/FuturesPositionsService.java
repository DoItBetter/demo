package com.kuainiu.qt.data.service;


import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.FuturesPositionReqSerBean;
import com.kuainiu.qt.data.service.bean.FuturesPositionSerBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/13
 * Time: 3:59 PM
 */
public interface FuturesPositionsService {
    void batchInsert(List<FuturesPositionSerBean> serBeanList) throws ServiceException;

    List<FuturesPositionSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException;

    FuturesPositionSerBean findFuturesPosition(FuturesPositionReqSerBean reqSerBean) throws ServiceException;
}
