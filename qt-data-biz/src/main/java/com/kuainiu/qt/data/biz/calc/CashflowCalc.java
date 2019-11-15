package com.kuainiu.qt.data.biz.calc;

import com.kuainiu.qt.data.biz.bean.BalanceLogOutBean;
import com.kuainiu.qt.data.biz.bean.CashflowOutBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/22
 * Time: 4:22 PM
 */
public interface CashflowCalc {
    List<CashflowOutBean> convertToCashflow(List<BalanceLogOutBean> balanceLogList);

    BigDecimal calcUnits(List<CashflowOutBean> cashflowList);

    BigDecimal calcCashflowToday(List<CashflowOutBean> cashflowList);
}
