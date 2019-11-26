package com.kuainiu.qt.data.param;

import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.request.PortfolioQryRequest;
import com.kuainiu.qt.data.facade.request.StdRequest;
import com.kuainiu.qt.data.facade.request.StkPositionPnlRequest;


/**
 * 入参检查
 */
public class ParamCheckHandle {

  public static void checkPortfolioQryRequest(PortfolioQryRequest request) throws BizException {
    if (null == request.getPortfolioCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE);
    }
  }

  public static void checkPortfolioStdQryRequest(StdRequest request) throws BizException {
    if (null == request.getPortfolioCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE);
    }
  }

  public static void checkStkPositionPnlRequest(StkPositionPnlRequest request) throws BizException {
    if (null == request) {
      throw new BizException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE);
    }
  }
}
