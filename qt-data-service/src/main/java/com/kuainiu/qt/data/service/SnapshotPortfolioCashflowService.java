package com.kuainiu.qt.data.service;


import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioCashflowSerBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/13
 * Time: 3:58 PM
 */
public interface SnapshotPortfolioCashflowService {
    void batchInsert(List<SnapshotPortfolioCashflowSerBean> serBeanList) throws ServiceException;

    List<SnapshotPortfolioCashflowSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException;
}
