package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.exception.ServiceException;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/16
 * Time: 5:24 PM
 */
public interface AidcQryService {

    boolean isTransToday() throws ServiceException;
}
