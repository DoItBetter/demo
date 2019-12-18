package com.cx.qt.data.facade;

import com.cx.qt.data.facade.request.StkPositionRequest;
import com.cx.qt.data.facade.response.StkPositionResponse;

public interface QtDataStkPositionFacade {

    StkPositionResponse findStkPosition(StkPositionRequest request);
}
