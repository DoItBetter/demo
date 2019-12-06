package com.kuainiu.qt.data.biz.facade.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.biz.StkPositionBiz;
import com.kuainiu.qt.data.biz.bean.StkPositionInBean;
import com.kuainiu.qt.data.biz.bean.StkPositionOutBean;
import com.kuainiu.qt.data.facade.QtDataStkPositionFacade;
import com.kuainiu.qt.data.facade.request.StkPositionRequest;
import com.kuainiu.qt.data.facade.response.StkPositionResponse;
import com.kuainiu.qt.data.param.ParamCheckHandle;
import com.kuainiu.qt.data.util.BizBeanUtils;
import com.kuainiu.qt.data.util.BizResponseUtils;
import com.kuainiu.qt.data.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class QtDataStkPositionFacadeImpl implements QtDataStkPositionFacade {
    @Autowired
    StkPositionBiz stkPositionBiz;

    @Override
    public StkPositionResponse findStkPosition(StkPositionRequest request) {
        log.info("[get stk Pnl]request={}", JSON.toJSONString(request));
        StkPositionResponse response = new StkPositionResponse();
        try {
            ParamCheckHandle.checkStkPositionPnlRequest(request);
            StkPositionInBean inBean = BizBeanUtils.buildStkPositionQryInBean(request);
            StkPositionOutBean outBean = stkPositionBiz.findStkPosition(inBean);
            response = BizResponseUtils.buildStkPositionQryResponse(outBean);
            ResponseUtils.success(response);
        } catch (Exception e) {
            log.error("[get stk Pnl fail]", e);
            ResponseUtils.sysError(response, e);
        }
        log.info("[get stk Pnl response] response ={}", JSON.toJSONString(response));
        return response;
    }
}
