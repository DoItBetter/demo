package com.cx.qt.data.dal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SnapshotFuturesAccount {
    private Integer id;

    private String snapshotCode;

    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private Date belongTime;

    private BigDecimal totalValue;

    private BigDecimal cash;

    private BigDecimal frzCash;

    private BigDecimal margin;

    private BigDecimal frzMargin;

    private BigDecimal marketValue;

    private BigDecimal dailyPnl;

    private BigDecimal holdingPnl;

    private BigDecimal realizedPnl;

    private BigDecimal transCost;

    private Date createTime;

    private Date updateTime;

    private Date endBelongTime;
}