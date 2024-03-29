package com.cx.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/6/18
 * Time: 3:47 PM
 */
@Data
public class CashflowSerBean extends BaseDataSerBean {
    private BigDecimal totalValue;

    private BigDecimal cashflowValue;

    private Date actionTime;
}
