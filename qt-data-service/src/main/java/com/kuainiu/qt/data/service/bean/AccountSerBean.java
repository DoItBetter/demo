package com.kuainiu.qt.data.service.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/5/21
 * Time: 8:43 PM
 */
@Data
public class AccountSerBean extends BaseDataSerBean{

    private Long id;

    private String realAccountCode;

    private String accountCode;

    private String accountName;

    private BigDecimal initAmount;

    private BigDecimal balanceAmount;

    private BigDecimal frzBalanceAmount;

    private String channelCode;

    private String accountType;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String portfolioCode;

    private String strategyCode;

    private String type;

    private String typeExtend;

    private BigDecimal balanceAmountForAdd = BigDecimal.ZERO;

    private BigDecimal frzBalanceAmountForAdd = BigDecimal.ZERO;

    private BigDecimal marginForAdd = BigDecimal.ZERO;

    private BigDecimal margin = BigDecimal.ZERO;

    private AccountExtendSerBean accountExtend = new AccountExtendSerBean();

    private Map<String, AccountExtendSerBean> accountExtendMap = new HashMap<>();

    private boolean isRedistributeMargin = false;

}
