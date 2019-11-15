package com.kuainiu.qt.data.biz.bean;

import com.kuainiu.qt.framework.common.bean.bizbean.BaseInBean;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/18
 * Time: 4:44 PM
 */
@Data
public class PortfolioInBean extends BaseInBean {
    private String portfolioCode;

    private String portfolioName;

    private String accessToken;

    private Date startDate;

    private String status;

    private List<StrategyInBean> strategyList = new ArrayList<>();

    private Boolean futuresRemote = false;

    /**
     * 是否是实时计算
     */
    private Boolean realTimeCalc = true;
}
