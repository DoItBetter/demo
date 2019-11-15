package com.kuainiu.qt.data.util;

import com.kuainiu.qt.data.dal.entity.SnapshotPortfolio;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;

public class BeanUtils {

    public static SnapshotPortfolio buildSnapshotPortfolio(SnapshotPortfolioReqSerBean reqSerBean) {
        SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
        BeanMapUtils.map(reqSerBean, snapshotPortfolio);
        return snapshotPortfolio;
    }

    public static SnapshotPortfolio buildSnapshotPortfolio(SnapshotPortfolioSerBean serBean) {
        SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
        BeanMapUtils.map(serBean, snapshotPortfolio);
        return snapshotPortfolio;
    }
}
