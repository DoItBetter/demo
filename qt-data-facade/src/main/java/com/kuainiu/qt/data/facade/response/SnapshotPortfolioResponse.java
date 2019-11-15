package com.kuainiu.qt.data.facade.response;

import com.kuainiu.qt.data.facade.bean.SnapshotFuturesAccountFacadeBean;
import com.kuainiu.qt.data.facade.bean.SnapshotStkAccountFacadeBean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SnapshotPortfolioResponse extends BaseDataResponse {
    private Long id;

    private String portfolioCode;

    private String snapshotCode;

    private BigDecimal cash;

    private BigDecimal frzCash;

    private BigDecimal totalReturns = BigDecimal.ZERO;

    private BigDecimal realtimeReturns = BigDecimal.ZERO;

    private BigDecimal annualizedReturns = BigDecimal.ZERO;

    private BigDecimal dailyPnl;

    private BigDecimal pnl;

    private BigDecimal marketValue;

    private BigDecimal totalValue;

    private BigDecimal units;

    private BigDecimal transCost;

    private Date startDate;

    private Date belongTime;

    private Date belongDate;

    private Date createTime;

    private Date updateTime;

    private BigDecimal std;

    private BigDecimal informationRatio = BigDecimal.ZERO;

    private BigDecimal baseRealtimeReturns = BigDecimal.ZERO;

    private List<SnapshotFuturesAccountFacadeBean> futuresAccountList = new ArrayList<>();

    private List<SnapshotStkAccountFacadeBean> stkAccountList = new ArrayList<>();
}
