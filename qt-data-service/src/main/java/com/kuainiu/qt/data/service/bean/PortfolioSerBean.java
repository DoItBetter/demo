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
    private Long id;

    private String portfolioCode;

    private String portfolioName;

    private String accessToken;

    private Date startDate;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String channelCode;

    private List<StrategySerBean> strategyList = new ArrayList<>();

    private AccountSerBean account;

    private Map<String, StrategySerBean> strategyMap = new HashMap<>();
}
