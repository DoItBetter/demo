package com.cx.qt.data.service.bean;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019-08-26
 * Time: 16:29
 */
@Data
public class PortfolioRunInfoBean  extends BaseDataSerBean{

    private BigDecimal baseTotalReturns;

    private int runDays;

    private JSONArray runDayList;
}
