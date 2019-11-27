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
    public FuturesPositionOutBean getHistoryPnl(FuturesPositionInBean inBean) throws BizException {
        log.info("[Biz][FUTURES] getHistoryPnl start, inBean = {}", inBean);
        FuturesPositionReqSerBean reqSerBean = BizBeanUtils.buildFuturesPositionReqSerBean(inBean);
        FuturesPositionSerBean serBean = new FuturesPositionSerBean();
        try {
            serBean = futuresPositionsService.findLastYesterday(reqSerBean);
        } catch (ServiceException e) {
            log.error("[Biz][FUTURES] getHistoryPnl fail , e={}", e);
            throw new BizException(QtDataRspCode.ERR_DB_SNAPSHOT_FUTURES_POSITION_QRY);
        }
        log.info("[Biz][FUTURES] getHistoryPnl end ", serBean);
        return BizBeanUtils.buildFuturesPositionOutBean(serBean);
    }
}
