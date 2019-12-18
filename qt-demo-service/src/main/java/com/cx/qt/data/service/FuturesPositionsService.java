package com.cx.qt.data.service;


import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.service.bean.FuturesPositionReqSerBean;
import com.cx.qt.data.service.bean.FuturesPositionSerBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/13
 * Time: 3:59 PM
 */
public interface FuturesPositionsService {
    void batchInsert(List<FuturesPositionSerBean> serBeanList) throws ServiceException;

    List<FuturesPositionSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException;

    FuturesPositionSerBean findFuturesPosition(FuturesPositionReqSerBean reqSerBean) throws ServiceException;
}
