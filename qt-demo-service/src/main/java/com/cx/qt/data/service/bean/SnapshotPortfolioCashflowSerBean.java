package com.cx.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/13
 * Time: 3:24 PM
 */
@Data
public class SnapshotPortfolioCashflowSerBean extends BaseDataSerBean {
    private Long id;

    private String snapshotCode;

    private BigDecimal totalValue;

    private BigDecimal cashflowValue;

    private Date actionTime;

    private Date createTime;

    private Date updateTime;
}
