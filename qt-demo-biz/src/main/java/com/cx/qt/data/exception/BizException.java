package com.cx.qt.data.exception;


import com.cx.qt.data.common.util.CommonConstant;
import com.cx.qt.data.facade.code.QtDataRspCode;
import com.cx.qt.data.facade.response.BaseDataResponse;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * 不需回滚异常
 */
//TODO
public class BizException extends Exception {

    private QtDataRspCode qtDataRspCode;

    private String code;

    private String msg;

    public BizException(QtDataRspCode QtDataRspCode){
        this.qtDataRspCode = QtDataRspCode;
        this.code = QtDataRspCode.getCode();
        this.msg = QtDataRspCode.getMessage();
    }

    public BizException(QtDataRspCode QtDataRspCode, Throwable t){
        super(t);
        this.qtDataRspCode = QtDataRspCode;
        this.code = QtDataRspCode.getCode();
        this.msg = QtDataRspCode.getMessage();
    }

    public BizException(QtDataRspCode QtDataRspCode, String msg, Throwable t){
        super(t);
        this.qtDataRspCode = QtDataRspCode;
        this.code = QtDataRspCode.getCode();
        this.msg = QtDataRspCode.getMessage();
        if (StringUtils.isNotBlank(msg)) {
            this.msg += CommonConstant.COMMA + msg;
        }
    }

    public BizException(QtDataRspCode QtDataRspCode, String msg){
        this.qtDataRspCode = QtDataRspCode;
        this.code = QtDataRspCode.getCode();
        this.msg = QtDataRspCode.getMessage();
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

    public BizException(String pattern, Object... arguments){
        super(MessageFormat.format(pattern, arguments));
    }
}
