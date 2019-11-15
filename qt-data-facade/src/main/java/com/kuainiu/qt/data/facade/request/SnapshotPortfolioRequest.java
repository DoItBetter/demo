package com.kuainiu.qt.data.facade.request;

import lombok.Data;

import java.util.Date;

@Data
public class SnapshotPortfolioRequest extends BaseDataRequest {
    private String portfolioCode;

    private Date startBelongTime;

    private Date endBelongTime;

    Integer pageNo = 0;

    Integer pageSize = 1000;
}
