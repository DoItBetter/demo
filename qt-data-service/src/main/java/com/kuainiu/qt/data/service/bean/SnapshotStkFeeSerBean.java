package com.kuainiu.qt.data.service.bean;

import lombok.Data;

@Data
public class SnapshotStkFeeSerBean extends BaseSerBean {
    private Long id;

    private Long snapshotStkAccountId;

    private String feeType;

    private String feeInfo;
}
