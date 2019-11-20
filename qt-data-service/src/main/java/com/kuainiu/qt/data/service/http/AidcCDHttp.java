package com.kuainiu.qt.data.service.http;


import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.http.bean.DCRunDaysHttpBean;
import com.kuainiu.qt.data.service.http.bean.DCSnapshotHttpBean;
import com.kuainiu.qt.data.service.http.bean.DCStkHttpBean;
import com.kuainiu.qt.data.service.http.bean.StkBarHttpBean;
import com.kuainiu.qt.data.service.http.request.PortfolioTimeRequest;
import com.kuainiu.qt.data.service.http.request.StockEarningRate300Request;
import com.kuainiu.qt.data.service.http.request.aidc.AidcStkBarRequest;
import com.kuainiu.qt.data.service.http.request.aidc.DCFuturesSnapshotRequest;
import com.kuainiu.qt.data.service.http.request.aidc.DCPortfolioRunDaysRequest;
import com.kuainiu.qt.data.service.http.request.aidc.DCStkSnapshotRequest;
import com.kuainiu.qt.data.service.http.response.PortfolioTimeCountResponse;
import com.kuainiu.qt.data.service.http.response.PortfolioTimeListResponse;
import com.kuainiu.qt.data.service.http.response.StockEarningRate300Response;
import com.kuainiu.qt.data.service.http.response.aidc.InstrumentResponse;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jixuan
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
