package com.kuainiu.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.common.util.RedisConst;
import com.kuainiu.qt.data.common.util.RedisUtil;
import com.kuainiu.qt.data.dal.dao.SnapshotFuturesPositionDao;
import com.kuainiu.qt.data.dal.entity.SnapshotFuturesPosition;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.SnapshotFuturesPositionsService;
import com.kuainiu.qt.data.service.bean.SnapshotFuturesPositionsSerBean;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/13
 * Time: 4:00 PM
 */
@Service
@Slf4j
public class SnapshotFuturesPositionsServiceImpl implements SnapshotFuturesPositionsService {
    @Autowired
    SnapshotFuturesPositionDao snapshotFuturesPositionDao;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void batchInsert(List<SnapshotFuturesPositionsSerBean> serBeanList) throws ServiceException {
        if (null == serBeanList || serBeanList.size() == CommonConstant.ZERO) {
            log.warn("[batch insert SnapshotFuturesPosition empty]");
            return;
        }
        try {
            List<SnapshotFuturesPosition> cashflowList = BeanMapUtils.mapAsList(serBeanList, SnapshotFuturesPosition.class);
            snapshotFuturesPositionDao.batchInsert(cashflowList);
        } catch (Exception e) {
            log.error("[batch insert SnapshotFuturesPosition fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_POSITION_BATCH_ADD);
        }
    }

    @Override
    public List<SnapshotFuturesPositionsSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException {
        List<SnapshotFuturesPositionsSerBean> serBeanList = new ArrayList<>();

        try {
            List<SnapshotFuturesPosition> stkPositionList = snapshotFuturesPositionDao.getListBySnapshotCode(snapshotCode);
            if (null == stkPositionList || stkPositionList.size() <= CommonConstant.ZERO) {
                return serBeanList;
            }
            serBeanList = BeanMapUtils.mapAsList(stkPositionList, SnapshotFuturesPositionsSerBean.class);
        } catch (Exception e) {
            log.error("[qry SnapshotFuturesPosition fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_POSITION_QRY);
        }
        return serBeanList;

    }

    @Override
    public BigDecimal getPnl(String assetNo) throws ServiceException {

        try {
            //获取开始时间
            Date endCreateTime = QtDateUtils.getCurrDateStart();
            Date startCreateTime = QtDateUtils.getOtherDate(-8);
            SnapshotFuturesPosition position = new SnapshotFuturesPosition();
            position.setAssetNo(assetNo);
            position.setEndCreateTime(endCreateTime);
            position.setStartCreateTime(startCreateTime);
            position = snapshotFuturesPositionDao.getLastRecordBeforeToday(position);
            log.info("[qry pnl from db]{}", JSON.toJSONString(position));
            if (null == position) {
                return BigDecimal.ZERO;
            }
            return Optional.ofNullable(position.getPnl()).orElse(BigDecimal.ZERO);
        } catch (Exception e) {
            log.error("qry pnl from db fail", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_HISTORY_PNL_QRY);
        }
    }

    @Override
    public SnapshotFuturesPositionsSerBean getLastRecordHistory(String assetNo) throws ServiceException {
        String rediskey = RedisConst.KEY_FUTURES_LAST_SNAPSHOT + assetNo;
        SnapshotFuturesPositionsSerBean serBean = (SnapshotFuturesPositionsSerBean) redisUtil.get(rediskey);
        if (serBean != null) {
            return serBean;
        }
        serBean = new SnapshotFuturesPositionsSerBean();
        try {
            //获取开始时间
            Date endCreateTime = QtDateUtils.getCloseMarketYesterday();
            SnapshotFuturesPosition position = new SnapshotFuturesPosition();
            position.setAssetNo(assetNo);
            position.setEndCreateTime(endCreateTime);
            position = snapshotFuturesPositionDao.getLastRecordBeforeToday(position);
            if (null != position) {
                BeanMapUtils.map(position, serBean);
            }
            redisUtil.set(rediskey, serBean, QtDateUtils.getSecondsLefToday());
            log.info("[Serbice][Futures]futures ss from db,redisKey={},data={}", rediskey, JSON.toJSONString(position));
        } catch (Exception e) {
            log.error("futures ss from db", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_HISTORY_PNL_QRY);
        }
        return serBean;
    }
}
