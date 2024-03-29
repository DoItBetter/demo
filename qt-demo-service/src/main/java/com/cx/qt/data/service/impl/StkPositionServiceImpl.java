package com.cx.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.dal.dao.SnapshotStkPositionDao;
import com.cx.qt.data.dal.entity.SnapshotStkPosition;
import com.cx.qt.data.facade.code.QtDataRspCode;
import com.cx.qt.data.service.StkPositionService;
import com.cx.qt.data.service.bean.SnapshotStkPositionSerBean;
import com.cx.qt.data.service.bean.StkPositionReqSerBean;
import com.cx.qt.data.service.bean.StkPositionSerBean;
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
public class StkPositionServiceImpl implements StkPositionService {
    @Autowired
    SnapshotStkPositionDao snapshotStkPositionDao;

    @Override
    public void batchInsert(List<SnapshotStkPositionSerBean> serBeanList) throws ServiceException {
        if (null == serBeanList || serBeanList.size() == CommonConstant.ZERO) {
            log.warn("[batch insert SnapshotStkPosition empty]");
            return;
        }
        try {
            List<SnapshotStkPosition> stkPositionList = BeanMapUtils.mapAsList(serBeanList, SnapshotStkPosition.class);
            snapshotStkPositionDao.batchInsert(stkPositionList);
            log.info("批量入库的stkPositionList ={} ", stkPositionList);
        } catch (Exception e) {
            log.error("[batch insert SnapshotStkPosition fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_STK_POSITION_BATCH_ADD);
        }
    }

    @Override
    public List<SnapshotStkPositionSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException {

        List<SnapshotStkPositionSerBean> serBeanList = new ArrayList<>();

        try {
            List<SnapshotStkPosition> stkPositionList = snapshotStkPositionDao.getListBySnapshotCode(snapshotCode);
            if (null == stkPositionList || stkPositionList.size() <= CommonConstant.ZERO) {
                return serBeanList;
            }
            serBeanList = BeanMapUtils.mapAsList(stkPositionList, SnapshotStkPositionSerBean.class);
        } catch (Exception e) {
            log.error("[qry SnapshotStkPosition fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_STK_POSITION_QRY);
        }
        return serBeanList;
    }

    @Override
    public StkPositionSerBean findStkPosition(StkPositionReqSerBean stkPositionReqSerBean) throws ServiceException {
        StkPositionSerBean stkPositionSerBean = new StkPositionSerBean();
        SnapshotStkPosition snapshotStkPosition = new SnapshotStkPosition();
        log.info("[Service][STK] qry last position snapshot,assetNo={},strategyCode={}", stkPositionReqSerBean.getAssetNo(), stkPositionReqSerBean.getStrategyCode());
        try {
            BeanMapUtils.map(stkPositionReqSerBean, snapshotStkPosition);
            snapshotStkPosition = snapshotStkPositionDao.findOne(snapshotStkPosition);
            if (snapshotStkPosition == null) {
                return stkPositionSerBean;
            }
            BeanMapUtils.map(snapshotStkPosition, stkPositionSerBean);
        } catch (Exception e) {
            log.error("findStkPosition fail", e);
            log.error("findStkPosition fail, snapshotStkPosition ={}", snapshotStkPosition);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_STK_POSITION_QRY);
        }
        log.info("[Service][STK] qry last position snapshot,res={}", JSON.toJSONString(stkPositionSerBean));
        return stkPositionSerBean;
    }
}
