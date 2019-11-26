package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/9/21
 * Time: 4:05 PM
 */
@Data
public class BalanceLogSerBean extends BaseDataSerBean {
    private Long id;

    private String accountCode;

    private BigDecimal balanceStartAmount;

    private BigDecimal balanceEndAmount;

    private BigDecimal balanceFrzStartAmount;

    private BigDecimal balanceFrzEndAmount;

    private String type;

    private String typeExtend;

    private Date createTime;

    private Date updateTime;

    private List<String> typeList;

    private List<String> accountCodeList;
}
