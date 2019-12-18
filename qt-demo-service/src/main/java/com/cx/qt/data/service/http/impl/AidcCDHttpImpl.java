package com.cx.qt.demo.service.http.impl;

import com.alibaba.fastjson.JSON;
import com.cx.qt.demo.exception.ServiceException;
import com.cx.qt.demo.service.http.bean.*;
import com.cx.qt.demo.service.http.request.PortfolioTimeRequest;
import com.cx.qt.demo.service.http.request.aidc.AidcStkBarRequest;
import com.cx.qt.demo.service.http.request.aidc.DCFuturesSnapshotRequest;
import com.cx.qt.demo.service.http.request.aidc.DCPortfolioRunDaysRequest;
import com.cx.qt.demo.service.http.request.aidc.DCStkSnapshotRequest;
import com.cx.qt.demo.service.http.response.FuturesSnapshotResponse;
import com.cx.qt.demo.service.http.response.PortfolioTimeCountResponse;
import com.cx.qt.demo.service.http.response.PortfolioTimeListResponse;
import com.cx.qt.demo.service.http.response.StockEarningRate300Response;
import com.cx.qt.demo.service.http.response.aidc.AidcStkBarResponse;
import com.cx.qt.demo.service.http.response.aidc.DCBaseResponse;
import com.cx.qt.demo.service.http.response.aidc.InstrumentResponse;
import com.cx.qt.demo.service.http.response.aidc.StkSnapshotResponse;
import com.cx.qt.demo.facade.code.QtdemoRspCode;
import com.cx.qt.demo.service.http.AidcCDHttp;
import com.cx.qt.demo.service.http.demoHttpClient;
import com.cx.qt.demo.service.http.bean.*;
import com.cx.qt.demo.service.http.request.StockEarningRate300Request;
import com.cx.qt.demo.service.http.util.HttpSerBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.F
 * User: cx
 * Date: 2019-07-19
 * Time: 11:57
 */
@Service
@Slf4j
public class AidcCDHttpImpl implements AidcCDHttp {
    private final String AIDC_RESPONSE_SUCC = "0";

    @Value("${domain.aidc}")
    private String domian;

    @Override
    public PortfolioTimeCountResponse queryTimeCount(PortfolioTimeRequest request) throws ServiceException {
        String uri = "common/strategyExecutionCount";
        HttpSerBean httpSerBean = HttpSerBeanUtils.buildHttpSerBean(request, PortfolioTimeCountResponse.class, domian, uri);
        PortfolioTimeCountResponse response = (PortfolioTimeCountResponse) DemoHttpClient.post(httpSerBean);
        if (!StringUtils.equals(response.getCode(), AIDC_RESPONSE_SUCC)) {
            throw new ServiceException(response.getMsg());
        }
        return response;
    }

    @Override
    public PortfolioTimeListResponse queryTimeList(PortfolioTimeRequest request) throws ServiceException {
        String uri = "common/strategyExecutionHistory";
        HttpSerBean httpSerBean = HttpSerBeanUtils.buildHttpSerBean(request, PortfolioTimeListResponse.class, domian, uri);
        PortfolioTimeListResponse response = (PortfolioTimeListResponse) DemoHttpClient.post(httpSerBean);
        if (!StringUtils.equals(response.getCode(), AIDC_RESPONSE_SUCC)) {
            throw new ServiceException(response.getMsg());
        }
        return response;
    }

    @Override
    public StockEarningRate300Response queryEarningRate300(StockEarningRate300Request request) throws ServiceException {
        String uri = "data/mtk/stock/hs300/rm";
        HttpSerBean httpSerBean = HttpSerBeanUtils.buildHttpSerBean(request, StockEarningRate300Response.class, domian, uri);
        StockEarningRate300Response response = (StockEarningRate300Response) DemoHttpClient.post(httpSerBean);
        if (!StringUtils.equals(response.getCode(), AIDC_RESPONSE_SUCC)) {
            throw new ServiceException(response.getMsg());
        }
        return response;
    }

    @Override
    public Map<String, DCSnapshotHttpBean> qryFuturesSnapshot(DCFuturesSnapshotRequest request) throws ServiceException {
        Map<String, DCSnapshotHttpBean> futuresList;

        String uri = "data/mtk/future/snapshot";
        HttpSerBean httpSerBean = HttpSerBeanUtils.buildHttpSerBean(request, FuturesSnapshotResponse.class, domian, uri);
        FuturesSnapshotResponse response = (FuturesSnapshotResponse) DemoHttpClient.post(httpSerBean);
        if (!response.getCode().equals(AIDC_RESPONSE_SUCC)) {
            log.error("qry futures from AIDC fail,response={}", JSON.toJSONString(response));
            throw new ServiceException(QtDemoRspCode.ERR_AIDC_FUTURES_SNAPSHOT_QRY);
        }
        futuresList = response.getData();
        return futuresList;
    }

    @Override
    public DCRunDaysHttpBean qryPortfolioRunDays(DCPortfolioRunDaysRequest request) throws ServiceException {
        DCRunDaysHttpBean httpBean = new DCRunDaysHttpBean();
        String uri = "common/strategyExecutionHistory";
        HttpSerBean httpSerBean = HttpSerBeanUtils.buildHttpSerBean(request, DCBaseResponse.class, domian, uri);
        DCBaseResponse response = (DCBaseResponse) DemoHttpClient.post(httpSerBean);
        if (!StringUtils.equals(response.getCode(), AIDC_RESPONSE_SUCC)) {
            throw new ServiceException(response.getMsg());
        }
        httpBean = (DCRunDaysHttpBean) response.getData();
        httpBean.setRunDays((List<String>) response.getData());
        return httpBean;
    }

    @Override
    public Map<String, DCStkHttpBean> qryStkSnapshot(DCStkSnapshotRequest request) throws ServiceException {
        Map<String, DCStkHttpBean> futuresList;

        String uri = "data/mtk/stock/snapshot";
        HttpSerBean httpSerBean = HttpSerBeanUtils.buildHttpSerBean(request, StkSnapshotResponse.class, domian, uri);
        StkSnapshotResponse response = (StkSnapshotResponse) DemoHttpClient.post(httpSerBean);
        if (!response.getCode().equals(AIDC_RESPONSE_SUCC)) {
            log.error("qry stk from DC fail,response={}", JSON.toJSONString(response));
            throw new ServiceException(QtDemoRspCode.ERR_DC_STK_SNAPSHOT_QRY);
        }
        futuresList = response.getData();
        return futuresList;
    }

    @Override
    public InstrumentResponse qryInstrument(String assetNo) throws ServiceException {
        String uri = "common/instrument/" + assetNo;
        HttpGetSerBean httpGetSerBean = HttpSerBeanUtils.buildHttpGetSerBean(InstrumentResponse.class, domian, uri);
        InstrumentResponse response = (InstrumentResponse) DemoHttpClient.get(httpGetSerBean);
        if (!StringUtils.equals(response.getCode(), AIDC_RESPONSE_SUCC)) {
            throw new ServiceException(response.getMsg());
        }
        return response;
    }

    @Override
    public Map<String, StkBarHttpBean> qryStkBar(AidcStkBarRequest request) throws ServiceException {
        String uri = "data/mtk/stock/bar";
        HttpSerBean httpGetSerBean = HttpSerBeanUtils.buildHttpSerBean(request, AidcStkBarResponse.class, domian, uri);
        AidcStkBarResponse response = (AidcStkBarResponse) DemoHttpClient.post(httpGetSerBean);
        if (!StringUtils.equals(response.getCode(), AIDC_RESPONSE_SUCC)) {
            throw new ServiceException(response.getMsg());
        }
        return response.getData();
    }
}
