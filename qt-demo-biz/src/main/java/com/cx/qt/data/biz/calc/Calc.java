package com.cx.qt.demo.biz.calc;

import com.cx.qt.demo.biz.bean.BalanceLogOutBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/10/22
 * Time: 4:22 PM
 */
public interface Calc {
    List<CashflowOutBean> convertToCashflow(List<BalanceLogOutBean> balanceLogList);

    BigDecimal calcUnits(List<CashflowOutBean> cashflowList);

    BigDecimal calcCashflowToday(List<CashflowOutBean> cashflowList);
}
