package com.kuainiu.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.common.util.RedisUtil;
import com.kuainiu.qt.data.dal.dao.SnapshotFuturesPositionDao;
import com.kuainiu.qt.data.dal.entity.SnapshotFuturesPosition;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.FuturesPositionsService;
import com.kuainiu.qt.data.service.bean.FuturesPositionReqSerBean;
import com.kuainiu.qt.data.service.bean.FuturesPositionSerBean;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/13
 * Time: 4:00 PM
 */
@Service
@Slf4j
public class FuturesPositionsServiceImpl implements FuturesPositionsService {
    @Autowired
    SnapshotFuturesPositionDao snapshotFuturesPositionDao;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void batchInsert(List<FuturesPositionSerBean> serBeanList) throws ServiceException {
        if (null == serBeanList || serBeanList.size() == CommonConstant.ZERO) {
            log.warn("[batch insert SnapshotFuturesPosition empty]");
            return;
        }
        try {
            List<SnapshotFuturesPosition> futurePositionList = BeanMapUtils.mapAsList(serBeanList, SnapshotFuturesPosition.class);
            snapshotFuturesPositionDao.batchInsert(futurePositionList);
            log.info("批量入库的futurePositionList = {} " , futurePositionList);
        } catch (Exception e) {
            log.error("[batch insert SnapshotFuturesPosition fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_POSITION_BATCH_ADD);
        }
    }

    @Override
    public List<FuturesPositionSerBean> getListBySnapshotCode(String snapshotCode) throws ServiceException {
        List<FuturesPositionSerBean> serBeanList = new ArrayList<>();

        try {
            List<SnapshotFuturesPosition> stkPositionList = snapshotFuturesPositionDao.getListBySnapshotCode(snapshotCode);
            if (null == stkPositionList || stkPositionList.size() <= CommonConstant.ZERO) {
                return serBeanList;
            }
            serBeanList = BeanMapUtils.mapAsList(stkPositionList, FuturesPositionSerBean.class);
        } catch (Exception e) {
            log.error("[qry SnapshotFuturesPosition fail]", e);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_POSITION_QRY);
        }
        return serBeanList;

    }

    @Override
    public FuturesPositionSerBean findFuturesPosition(FuturesPositionReqSerBean reqSerBean) throws ServiceException {
        FuturesPositionSerBean futuresPositionSerBean = new FuturesPositionSerBean();
        SnapshotFuturesPosition futuresPosition = new SnapshotFuturesPosition();
        log.info("[Service][FUTURES] qry last position snapshot,assetNo={},strategyCode={}", reqSerBean.getAssetNo(), reqSerBean.getStrategyCode());
        try {
            BeanMapUtils.map(reqSerBean, futuresPosition);
            futuresPosition = snapshotFuturesPositionDao.findOne(futuresPosition);
            if (futuresPosition == null) {
                log.info("[FUTURES] qry last position snapshot");
                return futuresPositionSerBean;
            }
            BeanMapUtils.map(futuresPosition, futuresPositionSerBean);
        } catch (Exception e) {
            log.error("findFuturesPosition fail", e);
            log.error("findFuturesPosition fail, snapshotStkPosition ={}", futuresPosition);
            throw new ServiceException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_POSITION_QRY);
        }
        log.info("[Service][FUTURES] qry last position snapshot,res={}", JSON.toJSONString(futuresPositionSerBean));
        return futuresPositionSerBean;
    }
}
