package com.kuainiu.qt.data.exception;

import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.response.BaseDataResponse;
import com.kuainiu.qt.framework.common.util.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class ServiceRuntimeException extends RuntimeException{
    private QtDataRspCode qtDataRspCode;

    private String code;

    private String msg;

    public ServiceRuntimeException(QtDataRspCode qtDataRspCode){
        this.qtDataRspCode = qtDataRspCode;
        this.code = qtDataRspCode.getCode();
        this.msg = qtDataRspCode.getMessage();
    }

    public ServiceRuntimeException(QtDataRspCode qtDataRspCode, Throwable t){
        super(t);
        this.qtDataRspCode = qtDataRspCode;
        this.code = qtDataRspCode.getCode();
        this.msg = qtDataRspCode.getMessage();
    }

    public ServiceRuntimeException(QtDataRspCode qtDataRspCode, String msg, Throwable t){
        super(t);
        this.qtDataRspCode = qtDataRspCode;
        this.code = qtDataRspCode.getCode();
        this.msg = qtDataRspCode.getMessage();
        if (StringUtils.isNotBlank(msg)) {
            this.msg += CommonConstant.COMMA + msg;
        }
    }

    public ServiceRuntimeException(QtDataRspCode qtDataRspCode, String msg){
        this.qtDataRspCode = qtDataRspCode;
        this.code = qtDataRspCode.getCode();
        this.msg = qtDataRspCode.getMessage();
        if (StringUtils.isNotBlank(msg)) {
            this.msg += CommonConstant.COMMA + msg;
        }
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void exceptionToResponse(BaseDataResponse baseDataResponse){
        baseDataResponse.setCode(this.getCode());
        baseDataResponse.setMsg(this.getMsg());
        baseDataResponse.setException(this);
    }



    public ServiceRuntimeException(String pattern, Object... arguments){
        super(MessageFormat.format(pattern, arguments));
    }
}