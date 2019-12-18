package com.cx.qt.data.service;

import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.service.bean.SnapshotStkPositionSerBean;
import com.cx.qt.data.service.bean.StkPositionReqSerBean;
import com.cx.qt.data.service.bean.StkPositionSerBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/13
 * Time: 3:26 PM
 */
public interface StkPositionService {
    void batchInsert(List<SnapshotStkPositionSerBean> serBeanList) throws ServiceException;

    List<SnapshotStkPositionSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException;

    StkPositionSerBean findStkPosition(StkPositionReqSerBean stkPositionReqSerBean) throws ServiceException;
}
