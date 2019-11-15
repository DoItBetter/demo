package com.kuainiu.qt.data.biz;

import com.kuainiu.qt.data.biz.bean.PortfolioInBean;
import com.kuainiu.qt.data.biz.bean.PortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.exception.BizException;

import java.util.List;

public interface PortfolioQryBiz {

    PortfolioOutBean qryPortfolioStd(PortfolioInBean inBean) throws BizException;

    SnapshotPortfolioOutBean qryInfoRatio(SnapshotPortfolioInBean inBean) throws BizException;

    List<SnapshotPortfolioOutBean> qrySnapshotPortfolioList(SnapshotPortfolioInBean inBean) throws BizException;

    List<SnapshotPortfolioOutBean> qryLastRecordPerDay(SnapshotPortfolioInBean inBean) throws BizException;

    PortfolioOutBean qryPortfolioFromLocal(PortfolioInBean inBean) throws BizException;
}
