package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StkFeeSerBean extends BaseDataSerBean {

    private BigDecimal feeTrade;

    private BigDecimal feeTransfer;
    /**
     * 净佣金
     */
    private BigDecimal feeJyj;

    /**
     * 佣金
     */
    private BigDecimal feeYj;

    /**
     * 印花税
     */
    private BigDecimal feeYhs;

    /**
     * 过户费
     */
    private BigDecimal feeGhf;

    /**
     * 清算费
     */
    private BigDecimal feeQsf;

    /**
     * 交易规费
     */
    private BigDecimal feeJygf;

    /**
     * 经手费
     */
    private BigDecimal feeJsf;

    /**
     * 证管费
     */
    private BigDecimal feeZgf;

    /**
     * 其他费
     */
    private BigDecimal feeQtf;
}
