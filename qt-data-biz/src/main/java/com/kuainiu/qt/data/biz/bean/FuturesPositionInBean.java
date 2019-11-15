package com.kuainiu.qt.data.biz.bean;

import com.kuainiu.qt.framework.common.bean.bizbean.BaseInBean;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/7
 * Time: 11:00 AM
 */
@Data
public class FuturesPositionInBean extends BaseInBean {
    private String assetNo;

    private String channelCode;

    private String portfolioCode;

    private String strategyCode;
}