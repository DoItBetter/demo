package com.kuainiu.qt.data.biz.impl;

import com.kuainiu.qt.data.biz.StkPositionBiz;
import com.kuainiu.qt.data.biz.bean.PortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.StkPositionInBean;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.StkPositionService;
import com.kuainiu.qt.data.service.bean.SnapshotStkPositionSerBean;
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
    public PortfolioOutBean getPnl(StkPositionInBean inBean) {
        log.info("[Biz][Stk] getPnl start, inBean = {}", inBean);
        SnapshotStkPositionSerBean serBean = BizBeanUtils.buildStkPositionSerBean(inBean);
        try {
            serBean = stkPositionService.findLastYesterday(serBean);
        } catch (ServiceException e) {
            log.error("[Biz][Stk] getPnl fail , e={}", e);
        }
        log.info("[Biz][Stk] getPnl end ", serBean);
        return BizBeanUtils.buildStkPositionOutBean(serBean);
    }
}
