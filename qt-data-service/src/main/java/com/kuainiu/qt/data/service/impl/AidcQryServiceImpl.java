package com.kuainiu.qt.data.service.impl;

import com.kuainiu.qt.data.common.util.CommonConstant;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.common.util.RedisKeyUtils;
import com.kuainiu.qt.data.common.util.RedisUtil;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.AidcQryService;
import com.kuainiu.qt.data.service.http.AidcSHHttp;
import com.kuainiu.qt.data.service.http.request.aidc.AidcTradeDateRequest;
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
        return aidcSHHttp.qryTradeDate(request);
    }
}
