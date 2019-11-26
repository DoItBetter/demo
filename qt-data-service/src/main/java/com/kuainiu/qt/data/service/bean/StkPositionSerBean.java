package com.kuainiu.qt.data.service.bean;

import com.kuainiu.qt.data.service.bean.trans.StkAssetDetailFeeSerBean;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StkPositionSerBean extends BaseDataSerBean {
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

    private StkAssetDetailFeeSerBean stkFee;

    private String assetName;
}
