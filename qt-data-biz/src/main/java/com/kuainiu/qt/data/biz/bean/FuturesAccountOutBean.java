package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/7
 * Time: 10:59 AM
 */
@Data
public class FuturesAccountOutBean extends BaseOutBean {
    private String portfolioCode;

    private String strategyCode;

    private BigDecimal cash;

    private BigDecimal frzCash;

    private BigDecimal marketValue;

    private BigDecimal dailyPnl;

    private BigDecimal holdingPnl;

    private BigDecimal realizedPnl;

    private BigDecimal totalValue;

    private BigDecimal transCost;

    private BigDecimal frzMargin;

    private BigDecimal margin;

    private String accountCode;
}
