package com.kuainiu.qt.data.service.impl;

import com.kuainiu.qt.data.common.util.*;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.AidcQryService;
import com.kuainiu.qt.data.service.bean.aidc.RmReqSerBean;
import com.kuainiu.qt.data.service.bean.aidc.RmSerBean;
import com.kuainiu.qt.data.service.http.AidcCDHttp;
import com.kuainiu.qt.data.service.http.AidcSHHttp;
import com.kuainiu.qt.data.service.http.request.StockEarningRate300Request;
import com.kuainiu.qt.data.service.http.request.aidc.AidcTradeDateRequest;
import com.kuainiu.qt.data.service.http.response.StockEarningRate300Response;
import com.kuainiu.qt.data.service.http.response.aidc.InstrumentResponse;
import com.kuainiu.qt.data.util.SerBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/25
 * Time: 7:51 PM
 */
@Service
@Slf4j
public class AidcQryServiceImpl implements AidcQryService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    AidcSHHttp aidcSHHttp;

    @Autowired
    AidcCDHttp aidcCDHttp;

    @Override
    public boolean isTransToday() throws ServiceException {
        String redisKey = RedisKeyUtils.getIsTransTodayKey();
        Boolean isTransToday = (Boolean) redisUtil.get(redisKey);
        if (null == isTransToday) {
            List<String> runDayList = getRundayList(QtDateUtils.getCurrDate(), QtDateUtils.getCurrDate());
            isTransToday = runDayList.size() == CommonConstant.ONE;

            redisUtil.set(redisKey, isTransToday, QtDateUtils.getSecondsLefToday());
        }
        log.info("is trans today = {}", isTransToday);
        return isTransToday;
    }

    private List<String> getRundayList(Date startDate, Date endDate) throws ServiceException {
        AidcTradeDateRequest request = new AidcTradeDateRequest();
        request.setBegin_date(QtDateUtils.converToYMD(startDate));
        request.setEnd_date(QtDateUtils.converToYMD(endDate));
        request.setType(1);
        List<String> rundayList = aidcSHHttp.qryTradeDate(request);
        log.info("[AIDC] rundayList = {}", rundayList);
        return rundayList;
    }

    @Override
    public String getAssetName(String transboard, String assetNo) throws ServiceException {
        String buildAssetNo = transboard + assetNo;
        String redisKey = RedisConst.KEY_ASSET_NAME + buildAssetNo;
        String assetName = (String) redisUtil.get(redisKey);
        if (null != assetName) {
            return assetName;
        }

        try {
            log.info("[AIDC] qryInstrument request ={}", buildAssetNo);
            InstrumentResponse response = aidcCDHttp.qryInstrument(buildAssetNo);
            assetName = response.getData().getSymbol();
            log.info("[AIDC] qryInstrument response ={}", response);
        } catch (ServiceException e) {
            throw new ServiceException(QtDataRspCode.ERR_AIDC_ASSET_NAME, "[" + buildAssetNo + "]");
        }

        redisUtil.set(redisKey, assetName, QtDateUtils.getSecondsLefToday());
        return assetName;
    }

    @Override
    public Integer getPortfolioRundays(String portfolioCode, Date startDate) throws ServiceException {
        String redisKey = RedisKeyUtils.getPortfolioRunDaysKey(portfolioCode);
        Integer runDays = (Integer) redisUtil.get(redisKey);
        if (null == runDays) {
            List<String> runDayList = getRundayList(startDate, QtDateUtils.getCurrDate());
            runDays = runDayList.size();
            redisUtil.set(redisKey, runDays, QtDateUtils.getSecondsLefToday());
        }
        log.info("[AIDC] getPortfolioRundays , redisKey = {}, runDays = {}", redisKey, runDays);
        return runDays;
    }

    @Override
    public RmSerBean qryRm(RmReqSerBean reqSerBean) throws ServiceException {
        StockEarningRate300Request request = SerBeanUtils.buildRmRequest(reqSerBean);
        StockEarningRate300Response earningResponse = new StockEarningRate300Response();
        try {
            earningResponse = aidcCDHttp.queryEarningRate300(request);
            log.info("[AIDC] qryRm response ={}", earningResponse);
        } catch (ServiceException e) {
            log.error("[AIDC] qryRm response ={}, e={}", earningResponse, e);
            throw new ServiceException(QtDataRspCode.ERR_AIDC_QRY_RM_FAIL);
        }
        if (earningResponse.getData() == null) {
            log.error("[AIDC] qryRm data is null, response ={}", earningResponse);
            throw new ServiceException(QtDataRspCode.ERR_AIDC_QRY_RM_FAIL);
        }
        return SerBeanUtils.buildRmSerBean(earningResponse);
    }
}
