package com.kuainiu.qt.data.service.http.bean;

import com.kuainiu.qt.data.service.http.request.BaseDataHttpRequest;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/2
 * Time: 11:09 AM
 */
@Data
public class HttpSerBean {

    private BaseDataHttpRequest request;

    private Class responseClass;

    private String url;

    private String domain;

    private String uri;
}
