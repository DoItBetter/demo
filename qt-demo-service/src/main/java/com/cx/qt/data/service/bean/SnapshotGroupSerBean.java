package com.cx.qt.data.service.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/13
 * Time: 4:20 PM
 */
@Data
public class SnapshotGroupSerBean {
    List<FuturesPositionSerBean> futuresPositionSerBeanList = new ArrayList<>();

    List<SnapshotPortfolioCashflowSerBean> portfolioCashflowSerBeanList = new ArrayList<>();

    List<SnapshotStkPositionSerBean> stkPositionSerBeanList = new ArrayList<>();

    SnapshotPortfolioSerBean portfolioSerBean;

    List<SnapshotFuturesAccountSerBean> futuresAccountList = new ArrayList<>();

    List<SnapshotStkAccountSerBean> stkAccountList = new ArrayList<>();
}
