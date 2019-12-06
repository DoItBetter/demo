package com.kuainiu.qt.data.facade;

import com.kuainiu.qt.data.facade.request.FuturesPositionRequest;
import com.kuainiu.qt.data.facade.response.FuturesPositionResponse;

public interface QtDataFuturesPositionFacade {

    FuturesPositionResponse findFuturesPosition(FuturesPositionRequest request);
}
