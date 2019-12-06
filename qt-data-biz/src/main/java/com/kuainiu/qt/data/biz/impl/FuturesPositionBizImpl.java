package com.kuainiu.qt.data.biz.impl;

import com.kuainiu.qt.data.biz.FuturesPositionBiz;
import com.kuainiu.qt.data.biz.bean.FuturesPositionInBean;
import com.kuainiu.qt.data.biz.bean.FuturesPositionOutBean;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.FuturesPositionsService;
import com.kuainiu.qt.data.service.bean.FuturesPositionReqSerBean;
import com.kuainiu.qt.data.service.bean.FuturesPositionSerBean;
import com.kuainiu.qt.data.util.BizBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FuturesPositionBizImpl implements FuturesPositionBiz {
    @Autowired
    FuturesPositionsService futuresPositionsService;

    @Override
    public FuturesPositionOutBean findFuturesPosition(FuturesPositionInBean inBean) throws BizException {
        log.info("[Biz][FUTURES] findFuturesPosition start, inBean = {}", inBean);
        FuturesPositionReqSerBean reqSerBean = BizBeanUtils.buildFuturesPositionReqSerBean(inBean);
        FuturesPositionSerBean serBean = new FuturesPositionSerBean();
        try {
            serBean = futuresPositionsService.findFuturesPosition(reqSerBean);
        } catch (ServiceException e) {
            log.error("[Biz][FUTURES] findFuturesPosition fail , e={}", e);
            throw new BizException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_POSITION_QRY);
        }
        log.info("[Biz][FUTURES] findFuturesPosition end ", serBean);
        return BizBeanUtils.buildFuturesPositionOutBean(serBean);
    }
}
