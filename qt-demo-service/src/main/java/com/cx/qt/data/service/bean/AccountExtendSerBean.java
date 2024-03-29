package com.cx.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/9/26
 * Time: 2:15 PM
 */
@Data
public class AccountExtendSerBean extends BaseDataSerBean {
    private Integer marginId;

    private BigDecimal margin = BigDecimal.ZERO;

    private Integer id;

    private Integer accountId;

    private String extendType;

    private String extendInfo;

    private Date createTime;

    private Date updateTime;
}
