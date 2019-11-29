package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.SnapshotGroupSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;

import java.util.Date;
import java.util.List;

public interface SnapshotPortfolioService {
    SnapshotPortfolioSerBean getStdByPFCode(String portfolioCode) throws ServiceException;

    SnapshotPortfolioSerBean getInfoRatioByPFCode(String portfolioCode) throws ServiceException;

    List<SnapshotPortfolioSerBean> findSnapshotPortfolioList(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException;

    List<SnapshotPortfolioSerBean> findLastRecordPerDayByPFCode(String portfolioCode) throws ServiceException;

    boolean needRun() throws ServiceException;

    void setSnapshotPortfolioTx(SnapshotGroupSerBean serBean) throws ServiceException;

    SnapshotPortfolioSerBean getBeforeBelongTime(String portfolioCode, Date belongTime) throws ServiceException;

    void addOne(SnapshotPortfolioSerBean serBean) throws ServiceException;

    SnapshotPortfolioSerBean findByBelongTimeAndErrorFlag(SnapshotPortfolioReqSerBean snapshotPortfolioReqSerBean) throws ServiceException;

    /**
     * 取某一分钟infoRatio未计算的订单
     * @param portfolioCode
     * @param belongTime
     * @return
     */
    SnapshotPortfolioSerBean getPortfolioByBelongTime(String portfolioCode, Date belongTime) throws ServiceException;

    SnapshotPortfolioSerBean findOneOneMinuteAgo(String portfolioCode) throws ServiceException;

    SnapshotPortfolioSerBean getByBelongTimeAndErrorFlag(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException;

    SnapshotPortfolioSerBean getBalanceReturnStdByPortfolioCode(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException;

    void updateReturnsFields(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException;

    void updateInfoRatio(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException;
}
