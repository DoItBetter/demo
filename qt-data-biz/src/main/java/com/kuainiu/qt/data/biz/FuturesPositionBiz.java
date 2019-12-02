package com.kuainiu.qt.data.biz;

import com.kuainiu.qt.data.biz.bean.FuturesPositionInBean;
import com.kuainiu.qt.data.biz.bean.FuturesPositionOutBean;
import com.kuainiu.qt.data.exception.BizException;

public interface FuturesPositionBiz {

    FuturesPositionOutBean getHistoryPnl(FuturesPositionInBean inBean) throws BizException;
}
