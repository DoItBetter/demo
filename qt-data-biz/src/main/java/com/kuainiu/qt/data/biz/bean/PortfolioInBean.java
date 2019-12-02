package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/18
 * Time: 4:44 PM
 */
@Data
public class PortfolioInBean extends BaseDataInBean {

    private String portfolioCode;
    /**
     * 是否是实时计算
     */
    private Boolean realTimeCalc = true;
}
