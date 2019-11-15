package com.kuainiu.qt.data.biz.bean;

import com.kuainiu.qt.data.service.bean.SnapshotFuturesAccountSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotStkAccountSerBean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/8
 * Time: 7:07 PM
 */
@Data
public class SnapshotPortfolioOutBean extends BaseOutBean {
    private Long id;

    private String portfolioCode;

    private String snapshotCode;

    private BigDecimal cash;

    private BigDecimal frzCash;

    private BigDecimal totalReturns = BigDecimal.ZERO;

    private BigDecimal realtimeReturns = BigDecimal.ZERO;

    private BigDecimal annualizedReturns = BigDecimal.ZERO;

    private BigDecimal dailyPnl;

    private BigDecimal pnl;

    private BigDecimal marketValue;

    private BigDecimal totalValue;

    private BigDecimal units;

    private BigDecimal transCost;

    private Date startDate;

    private Date belongTime;

    private Date belongDate;

    private Date createTime;

    private Date updateTime;

    private BigDecimal std;

    private BigDecimal informationRatio = BigDecimal.ZERO;

    private BigDecimal baseRealtimeReturns = BigDecimal.ZERO;

    private List<SnapshotFuturesAccountSerBean> futuresAccountList = new ArrayList<>();

    private List<SnapshotStkAccountSerBean> stkAccountList = new ArrayList<>();
}
