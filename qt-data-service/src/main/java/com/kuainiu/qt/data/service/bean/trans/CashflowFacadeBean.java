package com.kuainiu.qt.data.service.bean.trans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/18
 * Time: 3:47 PM
 */
@Data
public class CashflowFacadeBean {
    private BigDecimal totalValue;

    private BigDecimal cashflowValue;

    private Date actionTime;
}