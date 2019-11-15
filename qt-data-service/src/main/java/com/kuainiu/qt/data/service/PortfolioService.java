package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.PortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.PortfolioSerBean;

import java.util.List;

public interface PortfolioService {
    List<PortfolioSerBean> findAll(PortfolioSerBean serBean) throws ServiceException;

    PortfolioSerBean qryPortfolio(PortfolioReqSerBean reqSerBean) throws ServiceException;
}
