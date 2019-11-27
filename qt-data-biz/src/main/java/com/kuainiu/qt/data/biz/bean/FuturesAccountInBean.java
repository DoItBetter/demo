package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/7
 * Time: 10:59 AM
 */
@Data
public class FuturesAccountInBean extends BaseDataInBean {
    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private String channelCode;

    private Boolean remote = false;
}
