package com.cx.qt.data.service.impl;

import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.dal.dao.SnapshotPortfolioCashflowDao;
import com.cx.qt.data.dal.entity.SnapshotPortfolioCashflow;
import com.cx.qt.data.facade.code.QtDataRspCode;
import com.cx.qt.data.service.SnapshotPortfolioCashflowService;
import com.cx.qt.data.service.bean.SnapshotPortfolioCashflowSerBean;
import com.cx.qt.framework.common.util.BeanMapUtils;
import com.cx.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/13
 * Time: 4:00 PM
 */
@Service
@Slf4j
public class SnapshotPortfolioCashflowServiceImpl implements SnapshotPortfolioCashflowService {
    @Autowired
    SnapshotPortfolioCashflowDao snapshotPortfolioCashflowDao;

    @Override
    public void batchInsert(List<SnapshotPortfolioCashflowSerBean> serBeanList) throws ServiceException {
        if (null == serBeanList || serBeanList.size() == CommonConstant.ZERO) {
            log.warn("[batch insert SnapshotPortfolioCashflow empty]");
            return;
        }
        try {
            List<SnapshotPortfolioCashflow> cashflowList = BeanMapUtils.mapAsList(serBeanList, SnapshotPortfolioCashflow.class);
            snapshotPortfolioCashflowDao.batchInsert(cashflowList);
            log.info("批量入库的cashflowList = {} " , cashflowList);
        } catch (Exception e) {
            log.error("[batch insert SnapshotPortfolioCashflow fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_PORTFOLIO_CASHFLOW_BATCH_ADD);
        }
    }

    @Override
    public List<SnapshotPortfolioCashflowSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException {
        List<SnapshotPortfolioCashflowSerBean> serBeanList = new ArrayList<>();

        try {
            List<SnapshotPortfolioCashflow> cashflowList = snapshotPortfolioCashflowDao.getListBySnapshotCode(snapshotCode);
            if (null == cashflowList || cashflowList.size() <= CommonConstant.ZERO) {
                return serBeanList;
            }
            serBeanList = BeanMapUtils.mapAsList(cashflowList, SnapshotPortfolioCashflowSerBean.class);
        } catch (Exception e) {
            log.error("[qry SnapshotPortfolioCashflow fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_PORTFOLIO_CASHFLOW_QRY);
        }
        return serBeanList;
    }
}
