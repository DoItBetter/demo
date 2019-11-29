package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/8
 * Time: 6:09 PM
 */
@Data
public class SnapshotPortfolioReqSerBean extends BaseQryReqSerBean {

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
}
