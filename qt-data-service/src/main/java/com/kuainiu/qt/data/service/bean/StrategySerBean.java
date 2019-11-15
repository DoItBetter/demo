package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/9/9
 * Time: 9:23 PM
 */
@Data
public class StrategySerBean extends BaseSerBean {
    private Integer id;

    private String strategyCode;

    private String portfolioCode;

    private String strategyName;

    private String strategyDesc;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String accountCode;

    private String channelCode;

    private String accountStatus;

    private String strategyAccountStatus;

    private Map<String, AccountSerBean> accountMap = new HashMap<>();
}
