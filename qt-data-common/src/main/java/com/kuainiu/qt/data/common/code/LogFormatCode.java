package com.kuainiu.qt.data.common.code;

public enum LogFormatCode {
    /**
     * request
     */
    HTTP_REQUEST("[HTTP - request]url: {} request {}", "http请求[urq]"),
    /**
     * response
     */
    HTTP_RESPONSE("[HTTP - response]url: {} response {} ", "http请求返回[urq]"),
    /**
     * error
     */
    HTTP_ERROR("[HTTP]msg: {} url {}", "http请求[mu]"),

    ;
    private String format;

    private String desc;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    LogFormatCode(String format, String desc) {
        this.format = format;
        this.desc = desc;
    }
}
