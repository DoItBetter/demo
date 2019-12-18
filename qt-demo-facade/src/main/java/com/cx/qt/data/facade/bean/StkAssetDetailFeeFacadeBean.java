package com.cx.qt.data.facade.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StkAssetDetailFeeFacadeBean extends BaseFacadeBean {

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
