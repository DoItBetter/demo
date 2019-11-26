package com.kuainiu.qt.data.service.http.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/9/20
 * Time: 10:09 AM
 */
@Data
public class DCStkHttpBean extends BaseHttpBean {
    private BigDecimal last;

    private BigDecimal limit_up;

    private BigDecimal limit_down;

    private BigDecimal prev_close;

    private Date datetime;
}
