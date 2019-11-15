package com.kuainiu.qt.data.biz.bean;

import com.kuainiu.qt.data.service.bean.BaseSerBean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/22
 * Time: 4:22 PM
 */
@Data
public class CashflowOutBean extends BaseSerBean {
    private BigDecimal totalValue;

    private BigDecimal cashflowValue;

    private Date actionTime;
}