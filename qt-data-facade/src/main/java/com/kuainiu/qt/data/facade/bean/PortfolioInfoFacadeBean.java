package com.kuainiu.qt.data.facade.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PortfolioInfoFacadeBean extends BaseFacadeBean {

    private String portfolioCode;

    private BigDecimal cash = BigDecimal.ZERO;

    private BigDecimal frzCash= BigDecimal.ZERO;

    private BigDecimal totalReturns = BigDecimal.ZERO;

    private BigDecimal realtimeReturns = BigDecimal.ZERO;

    private BigDecimal dailyPnl = BigDecimal.ZERO;

    private BigDecimal marketValue = BigDecimal.ZERO;

    private BigDecimal totalValue = BigDecimal.ZERO;

    private BigDecimal units = BigDecimal.ZERO;

    private BigDecimal totalFund = BigDecimal.ZERO;

    private BigDecimal transCost = BigDecimal.ZERO;

    private BigDecimal pnl = BigDecimal.ZERO;

    private Date startDate;

    private BigDecimal annualizedReturns = BigDecimal.ZERO;

    private List<StkPositionFacadeBean> stkPositionList = new ArrayList<>();

    private List<FuturesPositionFacadeBean> futuresPositionList = new ArrayList<>();

    private List<CashflowFacadeBean> cashflowList = new ArrayList<>();

    private BigDecimal baseRealtimeReturns;

    private BigDecimal informationRatio;

    private BigDecimal alpha;

    private BigDecimal beta;

    private BigDecimal sharpeRatio;
}
