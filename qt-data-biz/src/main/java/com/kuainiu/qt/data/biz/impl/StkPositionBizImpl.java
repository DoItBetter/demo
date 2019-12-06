package com.kuainiu.qt.data.biz.impl;

import com.kuainiu.qt.data.biz.StkPositionBiz;
import com.kuainiu.qt.data.biz.bean.StkPositionInBean;
import com.kuainiu.qt.data.biz.bean.StkPositionOutBean;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.StkPositionService;
import com.kuainiu.qt.data.service.bean.StkPositionReqSerBean;
import com.kuainiu.qt.data.service.bean.StkPositionSerBean;
import com.kuainiu.qt.data.util.BizBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StkPositionBizImpl implements StkPositionBiz {
    @Autowired
    StkPositionService stkPositionService;

    @Override
    public StkPositionOutBean findStkPosition(StkPositionInBean inBean) {
        log.info("[Biz][Stk] findFuturesPosition start, inBean = {}", inBean);
        StkPositionReqSerBean reqSerBean = BizBeanUtils.buildStkPositionReqSerBean(inBean);
        StkPositionSerBean serBean = new StkPositionSerBean();
        try {
            serBean = stkPositionService.findStkPosition(reqSerBean);
        } catch (ServiceException e) {
            log.error("[Biz][Stk] findFuturesPosition fail , e={}", e);
        }
        log.info("[Biz][Stk] findFuturesPosition end ", serBean);
        return BizBeanUtils.buildStkPositionOutBean(serBean);
    }
}
