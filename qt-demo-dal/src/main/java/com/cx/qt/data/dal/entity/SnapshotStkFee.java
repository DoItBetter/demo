package com.cx.qt.data.dal.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SnapshotStkFee {
    private Long id;

    private Long snapshotStkAccountId;

    private String feeType;

    private String feeInfo;

    private Date createTime;

    private Date updateTime;
}