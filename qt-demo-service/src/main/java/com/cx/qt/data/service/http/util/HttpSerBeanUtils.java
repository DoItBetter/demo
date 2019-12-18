package com.cx.qt.demo.service.http.util;


import com.cx.qt.demo.service.http.bean.HttpGetSerBean;
import com.cx.qt.demo.service.http.bean.HttpSerBean;
import com.cx.qt.demo.service.http.request.BaseDemoHttpRequest;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/6/2
 * Time: 11:10 AM
 */
public class HttpSerBeanUtils {

    public static HttpSerBean buildHttpSerBean(BaseDemoHttpRequest request, Class response, String domain, String uri) {
        HttpSerBean httpSerBean = new HttpSerBean();
        httpSerBean.setRequest(request);
        httpSerBean.setResponseClass(response);
        String url = domain + uri;
        httpSerBean.setUrl(url);
        httpSerBean.setDomain(domain);
        httpSerBean.setUri(uri);
        return  httpSerBean;
    }

    public static HttpGetSerBean buildHttpGetSerBean(Class response, String domain, String uri) {
        HttpGetSerBean httpGetSerBean = new HttpGetSerBean();
        httpGetSerBean.setResponseClass(response);
        String url = domain + uri;
        httpGetSerBean.setUrl(url);
        httpGetSerBean.setDomain(domain);
        httpGetSerBean.setUri(uri);
        return  httpGetSerBean;
    }
}
