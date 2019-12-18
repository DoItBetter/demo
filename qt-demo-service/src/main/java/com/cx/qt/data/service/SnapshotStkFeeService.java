package com.cx.qt.data.service;

import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.service.bean.SnapshotStkFeeSerBean;

import java.util.List;

public interface SnapshotStkFeeService {
    public void batchInsert(List<SnapshotStkFeeSerBean> serBeanList) throws ServiceException;
}
