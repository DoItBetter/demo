package com.cx.qt.demo.service.http.bean;

import com.cx.qt.demo.service.http.request.BaseDemoHttpRequest;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/6/2
 * Time: 11:09 AM
 */
@Data
public class HttpSerBean {

    private BaseDemoHttpRequest request;

    private Class responseClass;

    private String url;

    private String domain;

    private String uri;
}
