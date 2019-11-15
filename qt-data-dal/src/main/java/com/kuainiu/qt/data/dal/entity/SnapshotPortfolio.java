package com.kuainiu.qt.data.dal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SnapshotPortfolio {
    private Long id;

    private String snapshotCode;

    private String portfolioCode;

    private BigDecimal cash;

    private BigDecimal frzCash;

    private BigDecimal totalReturns;

    private BigDecimal realtimeReturns;

    private BigDecimal annualizedReturns;

    private BigDecimal dailyPnl;

    private BigDecimal pnl;

    private BigDecimal marketValue;

    private BigDecimal totalValue;

    private BigDecimal units;

    private BigDecimal transCost;

    private BigDecimal totalRealtimeReturns;

    private BigDecimal baseReturns;

    private BigDecimal baseRealtimeReturns;

    private BigDecimal balanceReturns;

    private BigDecimal informationRatio;

    private String errorFlag;

    private Date startDate;

    private Date belongTime;

    private Date belongDate;

    private Date createTime;

    private Date updateTime;

    private Date startBelongTime;

    private Date endBelongTime;

    private Integer offset;

    private Integer limit;

    private String order;

    private BigDecimal std;

    private List<SnapshotFuturesAccount> futuresAccountList = new ArrayList<>();

    private List<SnapshotStkAccount> stkAccountList = new ArrayList<>();
}