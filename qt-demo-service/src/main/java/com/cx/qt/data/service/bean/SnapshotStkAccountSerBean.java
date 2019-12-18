package com.cx.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SnapshotStkAccountSerBean extends BaseDataSerBean {
    private Integer id;

    private String snapshotCode;

    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private BigDecimal totalValue = BigDecimal.ZERO;

    private BigDecimal cash = BigDecimal.ZERO;

    private BigDecimal frzCash = BigDecimal.ZERO;

    private BigDecimal marketValue = BigDecimal.ZERO;

    private Date belongTime;

    private Date createTime;

    private Date updateTime;

    List<SnapshotStkFeeSerBean> stkFeeList = new ArrayList<>();
}
