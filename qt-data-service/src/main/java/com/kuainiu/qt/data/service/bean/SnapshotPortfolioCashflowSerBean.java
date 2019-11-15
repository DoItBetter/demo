package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/13
 * Time: 3:24 PM
 */
@Data
public class SnapshotPortfolioCashflowSerBean extends BaseSerBean {
    private Long id;

    private String snapshotCode;

    private BigDecimal totalValue;

    private BigDecimal cashflowValue;

    private Date actionTime;

    private Date createTime;

    private Date updateTime;
}
