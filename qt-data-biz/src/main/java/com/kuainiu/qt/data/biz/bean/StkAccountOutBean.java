package com.kuainiu.qt.data.biz.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/5/22
 * Time: 9:56 PM
 */
@Data
public class StkAccountOutBean extends BaseOutBean {
    private String portfolioCode;

    private String strategyCode;

    private String accountCode;

    private String channelCode;

    /**
     * 可用余额
     */
    private BigDecimal cash;

    /**
     * 冻结余额
     */
    private BigDecimal frzCash;

    /**
     * 市值，单位(元)
     */
    private BigDecimal marketValue;

    /**
     * 总权益，单位(元)
     */
    private BigDecimal totalValue;

    /**
     * 当日费用明细
     */
    private StkFeeOutBean transactionCost;

    /**
     * 账户下的仓位
     */
    private List<StkPositionOutBean> positions = new ArrayList<>();
}