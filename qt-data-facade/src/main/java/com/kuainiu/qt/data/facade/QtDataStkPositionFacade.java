package com.kuainiu.qt.data.facade;

import com.kuainiu.qt.data.facade.request.StkPositionRequest;
import com.kuainiu.qt.data.facade.response.StkPositionResponse;

public interface QtDataStkPositionFacade {

    StkPositionResponse findStkPosition(StkPositionRequest request);
}
