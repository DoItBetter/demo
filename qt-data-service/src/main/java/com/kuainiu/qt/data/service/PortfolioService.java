package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.trans.PortfolioQrySerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioSerBean;

import java.util.List;

public interface PortfolioService {
    List<PortfolioSerBean> findAll(PortfolioReqSerBean reqSerBean) throws ServiceException;

    PortfolioQrySerBean qryPortfolio(PortfolioReqSerBean reqSerBean) throws ServiceException;
}
