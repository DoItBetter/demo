package com.kuainiu.qt.data.biz;

import com.kuainiu.qt.data.biz.bean.processor.PortfolioProcessorInBean;
import com.kuainiu.qt.data.exception.BizException;

public interface PortfolioBiz {
    void recordSnapshot(PortfolioProcessorInBean jobParam) throws BizException;
}
