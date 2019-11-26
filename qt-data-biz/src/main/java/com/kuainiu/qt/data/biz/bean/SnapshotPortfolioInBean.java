package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.util.Date;

@Data
public class SnapshotPortfolioInBean extends BaseDataInBean {

    private String portfolioCode;

    private Date startBelongTime;

    private Date endBelongTime;

    Integer pageNo = 0;

    Integer pageSize = 1000;
}
