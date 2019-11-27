package com.kuainiu.qt.data.facade;

import com.kuainiu.qt.data.facade.request.StkPositionPnlRequest;
import com.kuainiu.qt.data.facade.response.StkPositionPnlResponse;

public interface QtDataStkPositionFacade {

    StkPositionPnlResponse getPnl(StkPositionPnlRequest request);
}
