package com.kuainiu.qt.data.dal.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SnapshotPortfolioCashflow {
    private Long id;

    private String snapshotCode;

    private BigDecimal totalValue;

    private BigDecimal cashflowValue;

    private Date actionTime;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSnapshotCode() {
        return snapshotCode;
    }

    public void setSnapshotCode(String snapshotCode) {
        this.snapshotCode = snapshotCode == null ? null : snapshotCode.trim();
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getCashflowValue() {
        return cashflowValue;
    }

    public void setCashflowValue(BigDecimal cashflowValue) {
        this.cashflowValue = cashflowValue;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}