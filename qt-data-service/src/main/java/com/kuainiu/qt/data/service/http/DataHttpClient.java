package com.kuainiu.qt.data.service.http;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.http.bean.HttpGetSerBean;
import com.kuainiu.qt.data.service.http.bean.HttpSerBean;
import com.kuainiu.qt.data.service.http.response.BaseDataHttpResponse;
import com.kuainiu.qt.framework.common.code.LogFormatCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/5/31
 * Time: 6:03 PM
 */
@Slf4j
@SuppressWarnings("unchecked")
public class DataHttpClient {

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    private static final int CONNECTION_TIMEOUT = 5000;// 连接超时时间

    private static final int CONNECTION_REQUEST_TIMEOUT = 5000;// 请求超时时间

    private static final int SOCKET_TIMEOUT = 10000;// 数据读取等待超时

    private static final String DEFAULT_ENCODING = "UTF-8";// 默认编码

    private static final String HEAD_CONTENT_TYPE  = "Content-Type";// 默认编码

    private static final String CONTENT_TYPE_TEXT = "application/json";// 默认编码

    private static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.4.4;  en-us; Nexus 4 Build/JOP40D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2307.2 Mobile Safari/537.36";

    public static BaseDataHttpResponse post(HttpSerBean httpSerBean) throws ServiceException {

        String jsonParams = JSON.toJSONString(httpSerBean.getRequest());
        log.info(LogFormatCode.HTTP_REQUEST.getFormat(), httpSerBean.getUrl(), jsonParams);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setRedirectsEnabled(true)
                .build();

        HttpPost httpPost = new HttpPost(httpSerBean.getUrl());
        httpPost.setConfig(requestConfig);
        httpPost.setHeader(HEAD_CONTENT_TYPE,CONTENT_TYPE_TEXT);
        Class responseClass = httpSerBean.getResponseClass();
        if (Objects.isNull(responseClass)) {
            throw new ServiceException(QtDataRspCode.ERR_HTTP_RESPONSE_CLASS_NULL);
        }
        try {
            httpPost.setEntity(new StringEntity(jsonParams, ContentType.create(CONTENT_TYPE_TEXT, DEFAULT_ENCODING)));
            HttpResponse result = httpClient.execute(httpPost);
            String jsonReponse = EntityUtils.toString(result.getEntity());
            BaseDataHttpResponse response = (BaseDataHttpResponse) JSON.parseObject(jsonReponse, responseClass);
            log.info(LogFormatCode.HTTP_RESPONSE.getFormat(), httpSerBean.getUrl(), jsonReponse);
            return response;
        } catch (HttpHostConnectException e) {
            log.error("[HTTP]request fail,msg={},url={},reqeust={}", e.getMessage().toString(), httpSerBean.getUrl(), httpSerBean.getRequest());
            throw new ServiceException(QtDataRspCode.ERR_HTTP_CONNECT);

        } catch (Exception e) {
            log.error("[HTTP]request fail,msg={},url={},reqeust={}", e.getMessage().toString(), httpSerBean.getUrl(), httpSerBean.getRequest());
            log.error("[HTTP]reqeust fail,exception", e);
            throw new ServiceException(QtDataRspCode.ERR_HTTP_CONNECT, e.getMessage().toString());
        }
    }

    /**
     * 发送GET请求
     * @return JSON或者字符串
     * @throws Exception
     */
    public static BaseDataHttpResponse get(HttpGetSerBean httpGetSerBean) throws ServiceException {
        BaseDataHttpResponse baseTransHttpResponse = new BaseDataHttpResponse();
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            /**
             * 创建HttpClient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建URIBuilder
             */
            URIBuilder uriBuilder = new URIBuilder(httpGetSerBean.getUrl());
            /**
             * 创建HttpGet
             */
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            /**
             * 设置请求头部编码
             */
            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            /**
             * 设置返回编码
             */
            httpGet.setHeader(new BasicHeader("Accept", "*/*"));
            /**
             * 请求服务
             */
            response = client.execute(httpGet);
            /**
             * 获取响应吗
             */
            int statusCode = response.getStatusLine().getStatusCode();

            if (!"200".equals(statusCode)){
                /**
                 * 获取返回对象
                 */
                HttpEntity entity = response.getEntity();
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString(entity,"UTF-8");
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                Class responseClass = httpGetSerBean.getResponseClass();
                baseTransHttpResponse = (BaseDataHttpResponse) JSON.parseObject(result, responseClass);
                return baseTransHttpResponse;
            }else{
                log.error("[HTTP]request fail,url={}", httpGetSerBean.getUrl());
            }
        }catch (Exception e){
            log.error("[HTTP]request fail,msg={},url={}", e.getMessage(), httpGetSerBean.getUrl());
            throw new ServiceException(QtDataRspCode.ERR_HTTP_CONNECT, e.getMessage());
        }
        return baseTransHttpResponse;
    }
}
