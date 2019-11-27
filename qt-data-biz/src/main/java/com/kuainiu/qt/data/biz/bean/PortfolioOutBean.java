package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/18
 * Time: 4:45 PM
 */
@Data
public class PortfolioOutBean extends BaseDataOutBean {

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

    private List<StkPositionOutBean> stkPositionList = new ArrayList<>();

    private List<FuturesPositionOutBean> futuresPositionList = new ArrayList<>();

    private BigDecimal std = BigDecimal.ZERO;

    List<StkAccountOutBean> stkAccountList = new ArrayList<>();

    List<FuturesAccountOutBean> futuresAccountList = new ArrayList<>();

    private List<CashflowOutBean> cashflowList = new ArrayList<>();
}
