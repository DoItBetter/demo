package com.kuainiu.qt.data.biz.bean;

import com.kuainiu.qt.framework.common.bean.bizbean.BaseInBean;
import lombok.Data;

import java.util.Date;

@Data
public class SnapshotPortfolioInBean extends BaseInBean {

    private String portfolioCode;

    private Date startBelongTime;

    private Date endBelongTime;

    Integer pageNo = 0;

    Integer pageSize = 1000;
}
