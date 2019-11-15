package com.kuainiu.qt.data.biz.bean;

import com.kuainiu.qt.framework.common.bean.bizbean.BaseInBean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/3
 * Time: 9:11 PM
 */
@Data
public class FuturesTransInBean extends BaseInBean {
    private String qtTransId;

    private String qtOrderId;

    private String channelTransId;

    private String assetNo;

    private Integer transQty;

    private BigDecimal transPrice;

    private BigDecimal transCost;

    private String futuresType;

    private String transSide;

    private String positionEffect;

    private String submitStatus;

    private String status;

    private Date transTime;

    Integer pageNo = 0;

    Integer pageSize = 1000;

    private String portfolioCode;

    private String strategyCode;
}
