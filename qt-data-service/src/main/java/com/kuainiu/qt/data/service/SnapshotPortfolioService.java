package com.kuainiu.qt.data.service;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.bean.SnapshotGroupSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SnapshotPortfolioService {
    SnapshotPortfolioSerBean getStdByPFCode(String portfolioCode) throws ServiceException;

    SnapshotPortfolioSerBean getInfoRatioByPFCode(String portfolioCode) throws ServiceException;

    List<SnapshotPortfolioSerBean> findSnapshotPortfolioList(SnapshotPortfolioReqSerBean reqSerBean) throws ServiceException;

    List<SnapshotPortfolioSerBean> findLastRecordPerDayByPFCode(String portfolioCode) throws ServiceException;

    boolean needRun() throws ServiceException;

    void setSnapshotPortfolioTx(SnapshotGroupSerBean serBean) throws ServiceException;

    SnapshotPortfolioSerBean getBeforeBelongTime(String portfolioCode, Date belongTime) throws ServiceException;

    void addOne(SnapshotPortfolioSerBean serBean) throws ServiceException;

    SnapshotPortfolioSerBean findLastBeforeOpenMarket(String portfolioCode) throws ServiceException;

    void dataCheckAndRepair(Map<String, Object> params, String portfolioCode, Date belongTime, int checkMinutes);

    void computePortfolioInformationRatio(Map<String, Object> params, String portfolioCode, Date belongTime, String error_flag) throws ServiceException;
}
