package com.cx.qt.demo.service.http;


import com.cx.qt.demo.exception.ServiceException;
import com.cx.qt.demo.service.http.bean.DCRunDaysHttpBean;
import com.cx.qt.demo.service.http.bean.DCStkHttpBean;
import com.cx.qt.demo.service.http.request.PortfolioTimeRequest;
import com.cx.qt.demo.service.http.request.aidc.AidcStkBarRequest;
import com.cx.qt.demo.service.http.request.aidc.DCFuturesSnapshotRequest;
import com.cx.qt.demo.service.http.request.aidc.DCPortfolioRunDaysRequest;
import com.cx.qt.demo.service.http.request.aidc.DCStkSnapshotRequest;
import com.cx.qt.demo.service.http.response.PortfolioTimeCountResponse;
import com.cx.qt.demo.service.http.response.PortfolioTimeListResponse;
import com.cx.qt.demo.service.http.response.StockEarningRate300Response;
import com.cx.qt.demo.service.http.response.aidc.InstrumentResponse;
import com.cx.qt.demo.service.http.bean.DCSnapshotHttpBean;
import com.cx.qt.demo.service.http.bean.StkBarHttpBean;
import com.cx.qt.demo.service.http.request.StockEarningRate300Request;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019-07-19
 * Time: 11:43
 */
public interface AidcCDHttp {
    PortfolioTimeCountResponse queryTimeCount(PortfolioTimeRequest request)  throws ServiceException;

    PortfolioTimeListResponse queryTimeList(PortfolioTimeRequest request)  throws ServiceException;

    StockEarningRate300Response queryEarningRate300(StockEarningRate300Request request) throws ServiceException;

    Map<String, DCSnapshotHttpBean> qryFuturesSnapshot(DCFuturesSnapshotRequest request) throws ServiceException;

    DCRunDaysHttpBean qryPortfolioRunDays(DCPortfolioRunDaysRequest request) throws ServiceException;

    Map<String, DCStkHttpBean> qryStkSnapshot(DCStkSnapshotRequest request) throws ServiceException;

    InstrumentResponse qryInstrument(String assetNo) throws ServiceException;

    Map<String, StkBarHttpBean> qryStkBar(AidcStkBarRequest request) throws ServiceException;
}
