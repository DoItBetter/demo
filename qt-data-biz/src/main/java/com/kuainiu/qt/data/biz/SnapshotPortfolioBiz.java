package com.kuainiu.qt.data.biz;

import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioInformationRatioProcessorInBean;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;

import java.util.Date;

public interface SnapshotPortfolioBiz {
    SnapshotPortfolioOutBean qryLastBeforeOpenMarket(SnapshotPortfolioInBean inBean) throws BizException;

    void recordPortfolio(PortfolioInformationRatioProcessorInBean jobParam);

    void calcPortfolio(String portfolioCode, Date belongTime) throws ServiceException;

}
