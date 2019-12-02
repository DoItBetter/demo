package com.kuainiu.qt.data.biz;

import com.kuainiu.qt.data.biz.bean.StkPositionInBean;
import com.kuainiu.qt.data.biz.bean.StkPositionOutBean;
import com.kuainiu.qt.data.exception.ServiceException;

public interface StkPositionBiz {
    StkPositionOutBean getHistoryPnl(StkPositionInBean inBean) throws ServiceException;
}
