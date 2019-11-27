package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/6/3
 * Time: 9:12 PM
 */
@Data
public class FuturesTransOutBean extends BaseDataOutBean {
    private Long id;

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

    private Date createTime;

    private Date updateTime;
}
