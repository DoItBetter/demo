package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/5/23
 * Time: 9:59 AM
 */
@Data
public class StkPositionOutBean extends BaseOutBean {
    private String portfolioCode;

    private String strategyCode;
    /**
     * 股票代码
     */
    private String assetNo;

    private String accountCode;

    private String transBoard;

    /**
     * 持仓数量，单位(股)
     */
    private Integer qty;

    /**
     * 累积持仓盈亏，单位(元)
     */
    private BigDecimal pnl;

    /**
     * 当然盈亏
     */
    private BigDecimal dailyPnl;

    /**
     * 持仓盈亏
     */
    private BigDecimal holdingPnl;

    /**
     * 平仓盈亏
     */
    private BigDecimal realizedPnl;

    /**
     * 可交易股数，单位(股)
     */
    private Integer sellableQty;

    /**
     * 持仓市值，单位(元)
     */
    private BigDecimal marketValue;

    /**
     * 持仓比例%
     */
    private BigDecimal valuePercent;

    /**
     * 平均建仓成本，单位(元)
     */
    private BigDecimal avgPrice;

    private BigDecimal transCost;

    private Integer total;

    private StkAssetDetailFeeOutBean stkFee;

    private String assetName;
}
