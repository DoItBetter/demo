package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/9/23
 * Time: 8:05 PM
 */
@Data
public class StkAssetDetailFeeOutBean extends BaseDataOutBean {

    private String assetCode;

    private BigDecimal feeTransfer = BigDecimal.ZERO;

    private Integer feeTransferId;

    private BigDecimal feeTrade = BigDecimal.ZERO;

    private BigDecimal feeTradeForAdd = BigDecimal.ZERO;

    private Integer feeTradeId;

    private BigDecimal feeYhs = BigDecimal.ZERO;

    private Integer feeYhsId;

    private BigDecimal totalFee;
}
