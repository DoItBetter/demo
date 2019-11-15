package com.kuainiu.qt.data.biz.bean;

import com.kuainiu.qt.framework.common.bean.bizbean.BaseInBean;
import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/22
 * Time: 11:44 AM
 */
@Data
public class StrategyInBean extends BaseInBean {
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

    private String accountType;
}
