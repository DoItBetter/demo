package com.cx.qt.data.biz;

import com.cx.qt.data.exception.BizException;
import com.cx.qt.data.biz.bean.processor.PortfolioProcessorInBean;

public interface PortfolioBiz {
    void recordSnapshot(PortfolioProcessorInBean jobParam) throws BizException;
}
