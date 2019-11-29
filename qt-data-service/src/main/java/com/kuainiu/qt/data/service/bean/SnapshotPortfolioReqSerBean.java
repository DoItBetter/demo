package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/8
 * Time: 6:09 PM
 */
@Data
public class SnapshotPortfolioReqSerBean extends BaseQryReqSerBean {
    private String portfolioCode;

    private Date startBelongTime;

    private Date endBelongTime;

    private String errorFlag;
}
