package com.kuainiu.qt.data.util;

import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;
import com.kuainiu.qt.data.service.code.SnapshotPortfolioCode;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;

public class BizReqSerBeanUtils {
    public static SnapshotPortfolioReqSerBean buildSnapshotPortfolioReqSerBean(SnapshotPortfolioInBean inBean) {
        SnapshotPortfolioReqSerBean serBean = new SnapshotPortfolioReqSerBean();
        BeanMapUtils.map(inBean, serBean);
        Integer pageNo = null == inBean.getPageNo() ? 0: inBean.getPageNo();
        Integer limit = null == inBean.getPageSize() ? 1000: inBean.getPageSize();
        Integer offset = pageNo * limit;
        serBean.setLimit(limit);
        serBean.setOffset(offset);
        return serBean;
    }

    public static SnapshotPortfolioReqSerBean buildHistoryBaseReturnsReqSerBean(SnapshotPortfolioSerBean snapshotPortfolio) {
        SnapshotPortfolioReqSerBean reqSerBean = new SnapshotPortfolioReqSerBean();
        BeanMapUtils.map(snapshotPortfolio, reqSerBean);
        reqSerBean.setErrorFlag(SnapshotPortfolioCode.SUCCESS.getCode());
        reqSerBean.setEndBelongTime(QtDateUtils.getOpenMarket());
        return reqSerBean;
    }
}
