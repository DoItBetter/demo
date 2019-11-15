package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.BalanceLogSerBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/9/21
 * Time: 4:04 PM
 */
public interface BalanceLogService {
    void create(BalanceLogSerBean balanceLog) throws ServiceException;

    List<BalanceLogSerBean> findList(BalanceLogSerBean balanceLog) throws ServiceException;

    List<BalanceLogSerBean> findListCashflow(List<String> accountCodeList) throws ServiceException;
}

