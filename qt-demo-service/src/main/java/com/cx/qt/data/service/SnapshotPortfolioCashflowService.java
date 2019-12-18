package com.cx.qt.data.service;


import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.service.bean.SnapshotPortfolioCashflowSerBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/13
 * Time: 3:58 PM
 */
public interface SnapshotPortfolioCashflowService {
    void batchInsert(List<SnapshotPortfolioCashflowSerBean> serBeanList) throws ServiceException;

    List<SnapshotPortfolioCashflowSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException;
}
