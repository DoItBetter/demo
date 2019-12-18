package com.cx.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StkAssetDetailFeeSerBean extends BaseDataSerBean {

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
