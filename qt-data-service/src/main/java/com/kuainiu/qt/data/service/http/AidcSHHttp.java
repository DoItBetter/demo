package com.kuainiu.qt.data.service.http;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.http.request.aidc.AidcTradeDateRequest;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/21
 * Time: 9:42 PM
 */
public interface AidcSHHttp {
    List<String> qryTradeDate(AidcTradeDateRequest request) throws ServiceException;
}
