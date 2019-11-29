package com.kuainiu.qt.data.param;

import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.request.*;


/**
 * 入参检查
 */
public class ParamCheckHandle {

  public static void checkPortfolioQryRequest(PortfolioQryRequest request) throws BizException {
    if (null == request.getPortfolioCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE_NULL);
    }
  }

  public static void checkPortfolioStdQryRequest(StdRequest request) throws BizException {
    if (null == request.getPortfolioCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE_NULL);
    }
  }

  public static void checkStkPositionPnlRequest(StkPositionPnlRequest request) throws BizException {
    if (null == request.getStrategyCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_STRATEGY_CODE_NULL);
    }
    if (null == request.getAssetNo()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_ASSET_NO_NULL);
    }
  }

  public static void checkFuturesPositionPnlRequest(FuturesPositionPnlRequest request) throws BizException {
    if (null == request.getStrategyCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_STRATEGY_CODE_NULL);
    }
    if (null == request.getAssetNo()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_ASSET_NO_NULL);
    }
  }

  public static void checkInfoRatioQryRequest(InfoRatioRequest request) throws BizException {
    if (null == request.getPortfolioCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE_NULL);
    }
  }

  public static void checkQrySnapshotPFListRequest(PortfolioYieldRequest request) throws BizException {
    if (null == request.getPortfolioCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE_NULL);
    }
  }

  public static void checkQryLastRecordperDay(PortfolioLastRecordPerDayRequest request) throws BizException {
    if (null == request.getPortfolioCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE_NULL);
    }
  }

  public static void checkQryLastBeforeOpenMarketRequest(SnapshotPortfolioRequest request) throws BizException {
    if (null == request.getPortfolioCode()) {
      throw new BizException(QtDataRspCode.ERR_PARAM_PORTFOLIO_CODE_NULL);
    }
  }
}
