package com.cx.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/13
 * Time: 3:23 PM
 */
@Data
public class SnapshotStkPositionSerBean extends BaseDataSerBean {
    private Long id;

    private String snapshotCode;

    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private String assetNo;

    private String transBoard;

    private Integer qty;

    private Integer sellableQty;

    private BigDecimal pnl = BigDecimal.ZERO;

    private BigDecimal dailyPnl = BigDecimal.ZERO;

    private BigDecimal holdingPnl = BigDecimal.ZERO;

    private BigDecimal relizedPnl = BigDecimal.ZERO;

    private BigDecimal marketValue = BigDecimal.ZERO;

    private BigDecimal valuePercent = BigDecimal.ZERO;

    private BigDecimal avgPrice = BigDecimal.ZERO;

    private Date belongTime;

    private Date createTime;

    private Date updateTime;
}
