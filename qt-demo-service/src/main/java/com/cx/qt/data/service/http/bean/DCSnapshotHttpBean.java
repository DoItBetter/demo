package com.cx.qt.demo.service.http.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/25
 * Time: 7:34 PM
 */
@Data
public class DCSnapshotHttpBean extends BaseHttpBean {

    private BigDecimal last = BigDecimal.ZERO;

    private BigDecimal prevSettlement;

    private BigDecimal limitDown;

    private Date datetime;

    private BigDecimal high;

    private BigDecimal limitUp;
}
