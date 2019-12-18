package com.cx.qt.demo.service.http.impl;

import com.cx.qt.demo.exception.ServiceException;
import com.cx.qt.demo.service.http.AidcSHHttp;
import com.cx.qt.demo.service.http.DemoHttpClient;
import com.cx.qt.demo.service.http.bean.HttpSerBean;
import com.cx.qt.demo.service.http.request.aidc.AidcTradeDateRequest;
import com.cx.qt.demo.service.http.response.aidc.AidcTradeDateResponse;
import com.cx.qt.demo.service.http.util.HttpSerBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/10/22
 * Time: 9:48 PM
 */
@Slf4j
@Service
public class AidcSHHttpImpl implements AidcSHHttp {
    private final String AIDC_RESPONSE_SUCC = "0";

    @Value("${domain.aidc.sh}")
    private String domian;

    @Override
    public List<String> qryTradeDate(AidcTradeDateRequest request) throws ServiceException {
        String uri = "trading_date/list";
        HttpSerBean httpSerBean = HttpSerBeanUtils.buildHttpSerBean(request, AidcTradeDateResponse.class, domian, uri);
        AidcTradeDateResponse response = (AidcTradeDateResponse) DemoHttpClient.post(httpSerBean);
        if (!response.getCode().equals(AIDC_RESPONSE_SUCC)) {
            throw new ServiceException(response.getMsg());
        }
        return response.getData();
    }
}
