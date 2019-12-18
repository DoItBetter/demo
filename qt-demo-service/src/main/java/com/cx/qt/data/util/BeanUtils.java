package com.cx.qt.data.util;

import com.cx.qt.data.dal.entity.SnapshotPortfolio;
import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.service.bean.SnapshotPortfolioReqSerBean;
import com.cx.qt.data.service.bean.SnapshotPortfolioSerBean;
import com.cx.qt.framework.common.util.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanUtils {

    public static SnapshotPortfolio buildSnapshotPortfolio(SnapshotPortfolioReqSerBean reqSerBean) {
        SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
        BeanMapUtils.map(reqSerBean, snapshotPortfolio);
        return snapshotPortfolio;
    }

    public static SnapshotPortfolio buildSnapshotPortfolio(SnapshotPortfolioSerBean serBean) throws ServiceException {
        SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
        BeanMapUtils.map(serBean, snapshotPortfolio);
        return snapshotPortfolio;
    }
}
