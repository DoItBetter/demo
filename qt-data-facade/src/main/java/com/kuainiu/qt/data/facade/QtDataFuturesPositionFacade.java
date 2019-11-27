package com.kuainiu.qt.data.facade;

import com.kuainiu.qt.data.facade.request.FuturesPositionPnlRequest;
import com.kuainiu.qt.data.facade.response.FuturesPositionPnlResponse;

public interface QtDataFuturesPositionFacade {

    FuturesPositionPnlResponse getPnl(FuturesPositionPnlRequest request);
}
