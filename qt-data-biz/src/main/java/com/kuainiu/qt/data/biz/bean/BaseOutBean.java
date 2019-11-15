package com.kuainiu.qt.data.biz.bean;


import com.kuainiu.qt.data.facade.code.QtDataRspCode;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/5/21
 * Time: 11:12 AM
 */
public class BaseOutBean {
    private String code;

    private String msg;

    private Throwable exception;

    public String getCode(){
        return code;
    }

    public void setCode(String respCode){
        this.code = respCode;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void code(QtDataRspCode qtDataRspCode){
        this.code = qtDataRspCode.getCode();
        this.msg = qtDataRspCode.getMessage();
    }

    public Throwable getException(){
        return exception;
    }

    public void setException(Throwable exception){
        this.exception = exception;
    }

    @Override
    public String toString(){
        return "OrderOutBean [code=" + code + ", msg=" + msg + ", exception=" + exception.getMessage() + "]";
    }
}
