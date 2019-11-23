package com.kuainiu.qt.data.facade.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashflowFacadeBean extends BaseFacadeBean {
    private BigDecimal totalValue;

    private BigDecimal cashflowValue;

    private Date actionTime;
}
