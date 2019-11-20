package com.kuainiu.qt.data.service.bean;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Jixuan
 * Date: 2019-08-26
 * Time: 16:29
 */
@Data
public class PortfolioRunInfoBean {
    private BigDecimal baseTotalReturns;
    private int runDays;
    private JSONArray runDayList;

}
