package com.cx.qt.demo.util;

import com.cx.qt.demo.exception.BizException;
import com.cx.qt.demo.exception.ServiceException;
import com.cx.qt.demo.exception.ServiceRuntimeException;
import com.cx.qt.demo.facade.code.QtDemoRspCode;
import com.cx.qt.demo.facade.response.BaseDemoResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.remoting.TimeoutException;

@Slf4j
public class ResponseUtils {

    private ResponseUtils(){
    }

    public static final String SUCCESS_CDOE = QtDemoRspCode.SUCCESS.getCode();

    public static void sysError(BaseDemoResponse resp, Throwable e){
        if (e instanceof IllegalArgumentException) {
            resp.setCode(QtDemoRspCode.ERR_PARAM_ERROR.getCode());
            resp.setMsg(e.getMessage());
        } else if (e instanceof TimeoutException) {
            resp.setCode(QtDemoRspCode.SYS_TIMEOUT.getCode());
            resp.setMsg(QtDemoRspCode.SYS_TIMEOUT.getMessage());
        } else if (e instanceof ServiceException) {
            ServiceException se = (ServiceException) e;
            if (se.getCode() != null) {
                se.exceptionToResponse(resp);
            } else {
                resp.setErrorCodeAndException(QtDemoRspCode.ERR_BUSI_ERROR, e);
            }
        } else if (e instanceof ServiceRuntimeException) {
            ServiceRuntimeException se = (ServiceRuntimeException) e;
            if (se.getCode() != null) {
                se.exceptionToResponse(resp);
            } else {
                resp.setErrorCodeAndException(QtDemoRspCode.ERR_SYS_ERROR, e);
            }
        } else if (e instanceof BizException) {
            BizException be = (BizException) e;
            if (be.getCode() != null) {
                be.exceptionToResponse(resp);
            } else {
                resp.setErrorCodeAndException(QtDemoRspCode.ERR_BUSI_ERROR, e);
            }
        } else {
            resp.setCode(QtDemoRspCode.SYS_ERROR.getCode());
            resp.setMsg(QtDemoRspCode.SYS_ERROR.getMessage());
        }
//        log.error(e, "facade调用返回异常！code：{},msg:{}", resp.getCode(),
//                resp.getMsg());
    }

    public static void success(BaseDemoResponse resp){
        resp.setCode(SUCCESS_CDOE);
        resp.setMsg(QtDemoRspCode.SUCCESS.getMessage());
    }
}
