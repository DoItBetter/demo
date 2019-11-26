package com.kuainiu.qt.data.service.bean;

import lombok.Data;

@Data
public class SnapshotStkFeeSerBean extends BaseDataSerBean {
    private Long id;

    private Long snapshotStkAccountId;

    private String feeType;

    private String feeInfo;
}
