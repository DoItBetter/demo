package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/5/21
 * Time: 8:30 PM
 */
@Data
public class PortfolioSerBean {

    private String portfolioCode;

    private String portfolioName;

    private String accessToken;

    private Date startDate;

    private String status;

    private List<StrategySerBean> strategyList = new ArrayList<>();
}
