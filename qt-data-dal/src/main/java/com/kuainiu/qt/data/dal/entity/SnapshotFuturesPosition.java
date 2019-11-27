package com.kuainiu.qt.data.dal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SnapshotFuturesPosition {
    private Long id;

    private String snapshotCode;

    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private String assetNo;

    private BigDecimal pnl = BigDecimal.ZERO;

    private BigDecimal dailyPnl = BigDecimal.ZERO;

    private BigDecimal holdingPnl = BigDecimal.ZERO;

    private BigDecimal realizedPnl = BigDecimal.ZERO;

    private BigDecimal transCost = BigDecimal.ZERO;

    private BigDecimal margin = BigDecimal.ZERO;

    private BigDecimal frzMargin = BigDecimal.ZERO;

    private BigDecimal marketValue = BigDecimal.ZERO;

    private Integer closableBuyQty = 0;

    private Integer buyTodayQty = 0;

    private Integer buyAccountTodayQty = 0;

    private BigDecimal buyAvgOpenPrice = BigDecimal.ZERO;

    private BigDecimal buyAvgHoldingPrice = BigDecimal.ZERO;

    private Integer closableSellQty = 0;

    private Integer sellTodayQty = 0;

    private Integer sellAccountTodayQty = 0;

    private BigDecimal sellAvgOpenPrice = BigDecimal.ZERO;

    private BigDecimal sellAvgHoldingPrice = BigDecimal.ZERO;

    private Date belongTime;

    private Date createTime;

    private Date updateTime;

    private Date startCreateTime;

    private Date endCreateTime;

    private Date endBelongTime;
}