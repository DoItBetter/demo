package com.cx.qt.data.facade;

import com.cx.qt.data.facade.request.FuturesPositionRequest;
import com.cx.qt.data.facade.response.FuturesPositionResponse;

public interface QtDataFuturesPositionFacade {

    FuturesPositionResponse findFuturesPosition(FuturesPositionRequest request);
}
