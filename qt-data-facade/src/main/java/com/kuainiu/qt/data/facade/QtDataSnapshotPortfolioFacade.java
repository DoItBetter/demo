package com.kuainiu.qt.data.facade;

import com.kuainiu.qt.data.facade.request.*;
import com.kuainiu.qt.data.facade.response.*;

public interface QtDataSnapshotPortfolioFacade {

    PortfolioQryResponse qryPortfolio(PortfolioQryRequest request);

    StdResponse qryPortfolioStd(StdRequest request);

    InfoRatioResponse qryInfoRatio(InfoRatioRequest request);

    SnapshotPortfolioListResponse qrySnapshotPortfolioList(PortfolioYieldRequest request);

    SnapshotPortfolioListResponse qryLastRecordPerDay(PortfolioLastRecordPerDayRequest request);

    SnapshotPortfolioResponse findByBelongTimeAndErrorFlag(SnapshotPortfolioRequest request);
}
