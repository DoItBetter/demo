package com.kuainiu.qt.data.biz;

import com.kuainiu.qt.data.biz.bean.PortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.StkPositionInBean;
import com.kuainiu.qt.data.exception.ServiceException;

public interface StkPositionBiz {
    PortfolioOutBean getPnl(StkPositionInBean inBean) throws ServiceException;
}
