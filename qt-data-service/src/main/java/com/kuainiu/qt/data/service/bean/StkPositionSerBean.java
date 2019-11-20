package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StkPositionSerBean extends BaseSerBean {
    /**
     * 股票代码
     */
    private String assetNo;

    /**
     * 交易板块
     */
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

    /**
     * 合约名称
     */
    private String assetName;
}
