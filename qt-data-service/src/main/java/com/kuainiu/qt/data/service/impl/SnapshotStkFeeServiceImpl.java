package com.kuainiu.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.dal.dao.SnapshotStkFeeDao;
import com.kuainiu.qt.data.dal.entity.SnapshotStkFee;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.SnapshotStkFeeService;
import com.kuainiu.qt.data.service.bean.SnapshotStkFeeSerBean;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SnapshotStkFeeServiceImpl implements SnapshotStkFeeService {
    @Autowired
    SnapshotStkFeeDao snapshotStkFeeDao;
    @Override
    public void batchInsert(List<SnapshotStkFeeSerBean> serBeanList) throws ServiceException {
        if (null == serBeanList || serBeanList.size() == CommonConstant.ZERO) {
            log.warn("[batch insert SnapshotStkFee empty]");
            return;
        }
        try {
            List<SnapshotStkFee> stkAccountList = BeanMapUtils.mapAsList(serBeanList, SnapshotStkFee.class);
            snapshotStkFeeDao.batchInsert(stkAccountList);
        } catch (Exception e) {
            log.error("[Service][Snapshot]batch insert SnapshotStkFee fail", e);
            log.error("[Service][Snapshot]batch insert SnapshotStkFee fail,data={}", JSON.toJSONString(serBeanList));
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_STK_FEE_BATCH_ADD);
        }
    }
}
