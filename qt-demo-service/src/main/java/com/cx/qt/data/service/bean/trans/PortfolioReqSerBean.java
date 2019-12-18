package com.cx.qt.data.service.bean.trans;

import com.cx.qt.data.service.bean.BaseDataReqSerBean;
import lombok.Data;

@Data
public class PortfolioReqSerBean extends BaseDataReqSerBean {
    private String portfolioCode;

    /**
     * 是否是实时计算
     */
    private Boolean realTimeCalc = true;

    private String status;
}
