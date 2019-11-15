package com.kuainiu.qt.data.biz;

import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.exception.BizException;

public interface SnapshotPortfolioBiz {
    SnapshotPortfolioOutBean qryLastBeforeOpenMarket(SnapshotPortfolioInBean inBean) throws BizException;
}
