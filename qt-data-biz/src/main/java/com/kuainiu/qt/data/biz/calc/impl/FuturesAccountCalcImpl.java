package com.kuainiu.qt.data.biz.calc.impl;

import com.kuainiu.qt.data.biz.bean.FuturesAccountOutBean;
import com.kuainiu.qt.data.biz.bean.SnapshotFuturesAccountOutBean;
import com.kuainiu.qt.data.biz.calc.FuturesAccountCalc;
import com.kuainiu.qt.framework.common.util.CalculateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/28
 * Time: 12:55 PM
 */
@Service
public class FuturesAccountCalcImpl implements FuturesAccountCalc {
    @Override
    public BigDecimal calcTotalFund(FuturesAccountOutBean account) {
        return CalculateUtils.sumBigDecimal(account.getCash(), account.getFrzCash(), account.getMargin(), account.getFrzMargin());
    }

    @Override
    public BigDecimal calcTotalFund(SnapshotFuturesAccountOutBean account) {
        return CalculateUtils.sumBigDecimal(account.getCash(), account.getFrzCash(), account.getMargin(), account.getFrzMargin());
    }

    @Override
    public BigDecimal calcTotalFund(List<SnapshotFuturesAccountOutBean> accountList) {
        return null;
    }
}
