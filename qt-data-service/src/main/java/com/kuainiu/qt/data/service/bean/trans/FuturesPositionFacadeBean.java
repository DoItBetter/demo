package com.kuainiu.qt.data.service.bean.trans;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/6
 * Time: 6:40 PM
 */
@Data
public class FuturesPositionFacadeBean {

    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private String assetNo;

    private BigDecimal pnl;

    private BigDecimal dailyPnl;

    private BigDecimal holdingPnl;

    private BigDecimal realizedPnl;

    private BigDecimal transCost;

    private BigDecimal margin;

    private BigDecimal frzMargin;

    private BigDecimal marketValue;

    private Integer closableBuyQty;

    private Integer buyTodayQty;

    private Integer buyAccountTodayQty;

    private BigDecimal buyAvgOpenPrice;

    private BigDecimal buyAvgHoldingPrice;

    private Integer closableSellQty;

    private Integer sellTodayQty;

    private Integer sellAccountTodayQty;

    private BigDecimal sellAvgOpenPrice;

    private BigDecimal sellAvgHoldingPrice;
}
