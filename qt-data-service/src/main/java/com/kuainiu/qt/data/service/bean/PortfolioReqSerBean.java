package com.kuainiu.qt.data.service.bean;

import lombok.Data;

@Data
public class PortfolioReqSerBean extends BaseDataReqSerBean {
    private String portfolioCode;

    /**
     * 是否是实时计算
     */
    private Boolean realTimeCalc = true;
}
