package com.kuainiu.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.dal.dao.SnapshotStkPositionDao;
import com.kuainiu.qt.data.dal.entity.SnapshotStkPosition;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.SnapshotStkPositionService;
import com.kuainiu.qt.data.service.bean.SnapshotStkPositionSerBean;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/13
 * Time: 4:00 PM
 */
@Service
@Slf4j
public class SnapshotStkPositionServiceImpl implements SnapshotStkPositionService {
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
            log.info("批量入库的stkPositionList : " + stkPositionList);
            snapshotStkPositionDao.batchInsert(stkPositionList);
        } catch (Exception e) {
            log.error("[batch insert SnapshotStkPosition fail]", e);
//            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_STK_POSITION_BATCH_ADD);
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
    public SnapshotStkPositionSerBean findLastYesterday(SnapshotStkPositionSerBean snapshotStkPositionSerBean) throws ServiceException {
        log.info("[Service][STK] qry last position snapshot,assetNo={},strategyCode={}", snapshotStkPositionSerBean.getAssetNo(), snapshotStkPositionSerBean.getStrategyCode());
        try {
            SnapshotStkPosition snapshotStkPosition = new SnapshotStkPosition();
            BeanMapUtils.map(snapshotStkPositionSerBean, snapshotStkPosition);
            Date endBelongTime = QtDateUtils.isBeforeOpenMarket() ? QtDateUtils.getOpenMarketYesterday() : QtDateUtils.getOpenMarket();
            snapshotStkPosition.setEndBelongTime(endBelongTime);
            snapshotStkPosition = snapshotStkPositionDao.findOne(snapshotStkPosition);
            if (snapshotStkPosition != null) {
                BeanMapUtils.map(snapshotStkPosition, snapshotStkPositionSerBean);
            }
        } catch (Exception e) {
            log.error("findLastYesterday fail", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_STK_POSITION_QRY);
        }
        log.info("[Service][STK] qry last position snapshot,res={}", JSON.toJSONString(snapshotStkPositionSerBean));
        return snapshotStkPositionSerBean;
    }
}
