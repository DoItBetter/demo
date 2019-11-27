package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/16
 * Time: 5:24 PM
 */
public interface AidcQryService {

    boolean isTransToday() throws ServiceException;

    String getAssetName(String transboard, String assetNo) throws ServiceException;

    Integer getPortfolioRundays(String portfolioCode, Date startDate) throws ServiceException;

    BigDecimal qryRm(SnapshotPortfolioSerBean snapshotPortfolio) throws ServiceException;
}
