package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/3
 * Time: 9:13 PM
 */
@Data
public class FuturesOrderOutBean extends BaseOutBean {
    private Long id;

    private String qtOrderId;

    private String frontOrderId;

    private String channelOrderId;

    private String channelFrontId;

    private String channelSessionId;

    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private String futuresType;

    private String transSide;

    private String assetNo;

    private String channelCode;

    private String priceMode;

    private String positionEffect;

    private BigDecimal limitPrice;

    private BigDecimal avgPrice;

    private Integer orderQty;

    private Integer filledQty;

    private Integer unfilledQty;

    private BigDecimal frzMargin;

    private BigDecimal margin;

    private BigDecimal releasedMargin;

    private BigDecimal transCost;

    private String submitStatus;

    private String status;

    private String message;

    private Date orderSendTime;

    private Date orderConfirmTime;

    private Date orderCancelTime;

    private Date createTime;

    private Date updateTime;
}
