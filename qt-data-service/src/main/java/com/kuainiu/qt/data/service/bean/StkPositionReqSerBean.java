package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StkPositionReqSerBean extends BaseDataReqSerBean {

    private Long id;

    private String snapshotCode;

    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private String assetNo;

    private String transBoard;

    private Integer qty;

    private Integer sellableQty;

    private BigDecimal pnl;

    private BigDecimal dailyPnl;

    private BigDecimal holdingPnl;

    private BigDecimal relizedPnl;

    private BigDecimal marketValue;

    private BigDecimal valuePercent;

    private BigDecimal avgPrice;

    private Date belongTime;

    private Date endBelongTime;
}
