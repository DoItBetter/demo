package com.kuainiu.qt.data.service.bean;

import com.kuainiu.qt.framework.common.util.CommonConstant;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/7
 * Time: 11:05 AM
 */
@Data
public class FuturesPositionSerBean extends BaseSerBean {
    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private String assetNo;

    private BigDecimal pnl = BigDecimal.ZERO;

    private BigDecimal dailyPnl = BigDecimal.ZERO;

    private BigDecimal holdingPnl = BigDecimal.ZERO;

    private BigDecimal realizedPnl = BigDecimal.ZERO;

    private BigDecimal transCost = BigDecimal.ZERO;

    private BigDecimal margin = BigDecimal.ZERO;

    private BigDecimal frzMargin = BigDecimal.ZERO;

    private BigDecimal marketValue = BigDecimal.ZERO;

    private Integer closableBuyQty = CommonConstant.ZERO;

    private Integer buyTodayQty = CommonConstant.ZERO;

    private Integer buyAccountTodayQty = CommonConstant.ZERO;

    private BigDecimal buyAvgOpenPrice = BigDecimal.ZERO;

    private BigDecimal buyAvgHoldingPrice = BigDecimal.ZERO;

    private Integer closableSellQty = CommonConstant.ZERO;

    private Integer sellTodayQty = CommonConstant.ZERO;

    private Integer sellAccountTodayQty = CommonConstant.ZERO;

    private BigDecimal sellAvgOpenPrice = BigDecimal.ZERO;

    private BigDecimal sellAvgHoldingPrice = BigDecimal.ZERO;

}
