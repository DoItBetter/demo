package com.kuainiu.qt.data.dal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SnapshotStkAccount {
    private Integer id;

    private String snapshotCode;

    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private BigDecimal totalValue;

    private BigDecimal cash;

    private BigDecimal frzCash;

    private BigDecimal marketValue;

    private Date belongTime;

    private Date createTime;

    private Date updateTime;
}