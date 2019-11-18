package com.kuainiu.qt.data.util;

import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.exception.ServiceRuntimeException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.response.BaseDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.remoting.TimeoutException;

@Slf4j
public class ResponseUtils {

    private ResponseUtils(){
    }

    public static final String SUCCESS_CDOE = QtDataRspCode.SUCCESS.getCode();

    public static void sysError(BaseDataResponse resp, Throwable e){
        if (e instanceof IllegalArgumentException) {
            resp.setCode(QtDataRspCode.ERR_PARAM_ERROR.getCode());
            resp.setMsg(e.getMessage());
        } else if (e instanceof TimeoutException) {
            resp.setCode(QtDataRspCode.SYS_TIMEOUT.getCode());
            resp.setMsg(QtDataRspCode.SYS_TIMEOUT.getMessage());
        } else if (e instanceof ServiceException) {
            ServiceException se = (ServiceException) e;
            if (se.getCode() != null) {
                se.exceptionToResponse(resp);
            } else {
                resp.setErrorCodeAndException(QtDataRspCode.ERR_BUSI_ERROR, e);
            }
        } else if (e instanceof ServiceRuntimeException) {
            ServiceRuntimeException se = (ServiceRuntimeException) e;
            if (se.getCode() != null) {
                se.exceptionToResponse(resp);
            } else {
                resp.setErrorCodeAndException(QtDataRspCode.ERR_SYS_ERROR, e);
            }
        } else if (e instanceof BizException) {
            BizException be = (BizException) e;
            if (be.getCode() != null) {
                be.exceptionToResponse(resp);
            } else {
                resp.setErrorCodeAndException(QtDataRspCode.ERR_BUSI_ERROR, e);
            }
        } else {
            resp.setCode(QtDataRspCode.SYS_ERROR.getCode());
            resp.setMsg(QtDataRspCode.SYS_ERROR.getMessage());
        }
//        log.error(e, "facade调用返回异常！code：{},msg:{}", resp.getCode(),
//                resp.getMsg());
    }

    public static void success(BaseDataResponse resp){
        resp.setCode(SUCCESS_CDOE);
        resp.setMsg(QtDataRspCode.SUCCESS.getMessage());
    }
}
