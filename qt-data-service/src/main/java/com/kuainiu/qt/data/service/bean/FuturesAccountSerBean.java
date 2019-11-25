package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FuturesAccountSerBean extends BaseSerBean {

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
