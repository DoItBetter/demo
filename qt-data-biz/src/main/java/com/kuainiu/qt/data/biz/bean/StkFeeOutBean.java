package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/15
 * Time: 8:15 PM
 */
@Data
public class StkFeeOutBean extends BaseOutBean {
    /**
     * 佣金
     */
    private BigDecimal feeYj = BigDecimal.ZERO;

    /**
     * 印花税
     */
    private BigDecimal feeYhs = BigDecimal.ZERO;

    private BigDecimal feeTrade = BigDecimal.ZERO;

    private BigDecimal feeTransfer = BigDecimal.ZERO;
}
