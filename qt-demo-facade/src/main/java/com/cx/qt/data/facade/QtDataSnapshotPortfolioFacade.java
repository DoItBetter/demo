package com.cx.qt.data.facade;

import com.cx.qt.data.facade.request.*;
import com.cx.qt.data.facade.response.*;
import com.cx.qt.data.facade.request.*;
import com.cx.qt.data.facade.response.*;

public interface QtDataSnapshotPortfolioFacade {

    PortfolioQryResponse qryPortfolio(PortfolioQryRequest request);

    StdResponse qryPortfolioStd(StdRequest request);

    InfoRatioResponse qryInfoRatio(InfoRatioRequest request);

    SnapshotPortfolioListResponse qrySnapshotPortfolioList(PortfolioYieldRequest request);

    SnapshotPortfolioListResponse qryLastRecordPerDay(PortfolioLastRecordPerDayRequest request);

    SnapshotPortfolioResponse findByPFCodeBelongTimeAndErrorFlag(SnapshotPortfolioRequest request);
}
