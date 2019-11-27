package com.kuainiu.qt.data.biz.bean;

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
public class PortfolioInBean extends BaseDataInBean {
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
