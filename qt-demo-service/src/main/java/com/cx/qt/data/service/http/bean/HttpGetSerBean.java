package com.cx.qt.demo.service.http.bean;

import lombok.Data;

@Data
public class HttpGetSerBean {

    private Class responseClass;

    private String url;

    private String domain;

    private String uri;
}
