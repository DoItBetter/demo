package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.SnapshotStkFeeSerBean;

import java.util.List;

public interface SnapshotStkFeeService {
    public void batchInsert(List<SnapshotStkFeeSerBean> serBeanList) throws ServiceException;
}
