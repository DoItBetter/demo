package com.kuainiu.qt.data.facade.bean;

import lombok.Data;

@Data
public class SnapshotStkFeeFacadeBean {
    private Long id;

    private Long snapshotStkAccountId;

    private String feeType;

    private String feeInfo;
}