package com.cx.qt.data.facade.response;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/5/21
 * Time: 2:13 PM
 */
public class BaseReponse implements Serializable {

    private static final long serialVersionUID = -7916964510630016103L;

    private static final String SYSTEM_NAME = "[DATA]";

    private String msg;

    private String code;

    private Throwable exception;

    public void setMsg(String msg) {
        this.msg = SYSTEM_NAME + msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
