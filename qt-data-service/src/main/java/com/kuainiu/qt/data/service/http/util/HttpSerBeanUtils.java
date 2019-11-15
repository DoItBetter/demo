package com.kuainiu.qt.data.service.http.util;


import com.kuainiu.qt.data.service.http.bean.HttpGetSerBean;
import com.kuainiu.qt.data.service.http.bean.HttpSerBean;
import com.kuainiu.qt.data.service.http.request.BaseDataHttpRequest;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/2
 * Time: 11:10 AM
 */
public class HttpSerBeanUtils {

    public static HttpSerBean buildHttpSerBean(BaseDataHttpRequest request, Class response, String domain, String uri) {
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
