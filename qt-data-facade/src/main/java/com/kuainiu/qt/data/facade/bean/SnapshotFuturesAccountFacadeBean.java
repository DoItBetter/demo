package com.kuainiu.qt.data.facade.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SnapshotFuturesAccountFacadeBean extends BaseFacadeBean {

    private Integer id;

    private String snapshotCode;

    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private Date belongTime;

    private BigDecimal totalValue = BigDecimal.ZERO;

    private BigDecimal cash = BigDecimal.ZERO;

    private BigDecimal frzCash = BigDecimal.ZERO;

    private BigDecimal margin = BigDecimal.ZERO;;

    private BigDecimal frzMargin = BigDecimal.ZERO;

    private BigDecimal marketValue = BigDecimal.ZERO;

    private BigDecimal dailyPnl = BigDecimal.ZERO;

    private BigDecimal holdingPnl = BigDecimal.ZERO;

    private BigDecimal realizedPnl = BigDecimal.ZERO;

    private BigDecimal transCost = BigDecimal.ZERO;

    private Date createTime;

    private Date updateTime;

    private Date endBelongTime;
}
