package com.cx.qt.data.dal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class SnapshotPortfolioCashflow {
    private Long id;

    private String snapshotCode;

    private BigDecimal totalValue;

    private BigDecimal cashflowValue;

    private Date actionTime;

    private Date createTime;

    private Date updateTime;
}