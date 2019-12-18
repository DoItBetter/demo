package com.cx.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/8
 * Time: 5:52 PM
 */
@Data
public class SnapshotPortfolioSerBean extends BaseDataSerBean {
    private Long id;

    private String snapshotCode;

    private String portfolioCode;

    private BigDecimal cash;

    private BigDecimal frzCash;

    private BigDecimal totalReturns;

    private BigDecimal realtimeReturns;

    private BigDecimal annualizedReturns;

    private BigDecimal dailyPnl;

    private BigDecimal pnl;

    private BigDecimal marketValue;

    private BigDecimal totalValue;

    private BigDecimal units;

    private BigDecimal transCost;

    private BigDecimal totalRealtimeReturns;

    private BigDecimal baseReturns;

    private BigDecimal baseRealtimeReturns;

    private BigDecimal balanceReturns;

    private BigDecimal informationRatio;

    private String errorFlag;

    private Date startDate;

    private Date belongTime;

    private Date belongDate;

    private Date createTime;

    private Date updateTime;

    private Date startBelongTime;

    private Date endBelongTime;

    private Integer offset;

    private Integer limit;

    private String order;

    private BigDecimal std;

    private List<SnapshotFuturesAccountSerBean> futuresAccountList = new ArrayList<>();

    private List<SnapshotStkAccountSerBean> stkAccountList = new ArrayList<>();
}
