package com.kuainiu.qt.data.biz.facade.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.biz.FuturesPositionBiz;
import com.kuainiu.qt.data.biz.bean.FuturesPositionInBean;
import com.kuainiu.qt.data.biz.bean.FuturesPositionOutBean;
import com.kuainiu.qt.data.facade.QtDataFuturesPositionFacade;
import com.kuainiu.qt.data.facade.request.FuturesPositionRequest;
import com.kuainiu.qt.data.facade.response.FuturesPositionResponse;
import com.kuainiu.qt.data.param.ParamCheckHandle;
import com.kuainiu.qt.data.util.BizBeanUtils;
import com.kuainiu.qt.data.util.BizResponseUtils;
import com.kuainiu.qt.data.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Slf4j
public class QtDataFuturesPositionFacadeImpl implements QtDataFuturesPositionFacade {

    @Autowired
    FuturesPositionBiz futuresPositionBiz;
    @Override
    public FuturesPositionResponse findFuturesPosition(FuturesPositionRequest request) {
        log.info(" get futures position request={}", JSON.toJSONString(request));
        FuturesPositionResponse response = new FuturesPositionResponse();
        try {
            ParamCheckHandle.checkFuturesPositionPnlRequest(request);
            FuturesPositionInBean inBean = BizBeanUtils.buildFuturesPositionQryInBean(request);
            FuturesPositionOutBean outBean = futuresPositionBiz.findFuturesPosition(inBean);
            response = BizResponseUtils.buildFuturesPositionQryResponse(outBean);
            ResponseUtils.success(response);
        } catch (Exception e) {
            log.error(" get futures position fail ", e);
            ResponseUtils.sysError(response, e);
        }
        log.info("[get stk futures response] response ={}", JSON.toJSONString(response));
        return response;
    }
}
