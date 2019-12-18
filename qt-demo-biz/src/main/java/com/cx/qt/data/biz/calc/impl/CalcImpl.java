package com.cx.qt.data.biz.calc.impl;

import com.alibaba.fastjson.JSON;
import com.cx.qt.data.biz.bean.CashflowOutBean;
import com.cx.qt.data.biz.calc.CashflowCalc;
import com.cx.qt.data.common.util.QtDateUtils;
import com.cx.qt.framework.common.util.CalculateUtils;
import com.cx.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/10/22
 * Time: 4:26 PM
 */
@Service
@Slf4j
public class CalcImpl implements CashflowCalc {
    @Override
    public List<CashflowOutBean> convertToCashflow(List<BalanceLogOutBean> balanceLogList) {
        log.info("convert balance log to cashflow list,balanceList={}", JSON.toJSONString(balanceLogList));

        BigDecimal totalValue = BigDecimal.ZERO ;

        List<CashflowOutBean> cashflowList = new ArrayList<>();
        for (BalanceLogOutBean balanceLog : balanceLogList) {

            CashflowOutBean cashflowOutBean = new CashflowOutBean();
            BigDecimal cashflowValue = CalculateUtils.subtract(balanceLog.getBalanceEndAmount(), balanceLog.getBalanceStartAmount());
            totalValue = CalculateUtils.sumBigDecimal(totalValue, cashflowValue);
            cashflowOutBean.setActionTime(balanceLog.getCreateTime());
            cashflowOutBean.setCashflowValue(cashflowValue);
            cashflowOutBean.setTotalValue(totalValue);
            cashflowList.add(cashflowOutBean);
        }
        cashflowList = cashflowList.stream().sorted(Comparator.comparing(CashflowOutBean::getActionTime).reversed()).collect(Collectors.toList());
        return cashflowList;
    }

    @Override
    public BigDecimal calcUnits(List<CashflowOutBean> cashflowList) {
        int size = cashflowList.size();

        if (size == CommonConstant.ZERO) {
            return BigDecimal.ZERO;
        }
        return cashflowList.get(size - 1).getTotalValue();
    }

    @Override
    public BigDecimal calcCashflowToday(List<CashflowOutBean> cashflowList) {
        BigDecimal cashflowValue = BigDecimal.ZERO;
        for (CashflowOutBean cashflow : cashflowList) {
            if (QtDateUtils.isAfterCloseMarketYesterDay(cashflow.getActionTime())) {
                cashflowValue = CalculateUtils.sumBigDecimal(cashflowValue, cashflow.getCashflowValue());
            }
        }

        return cashflowValue;
    }
}
