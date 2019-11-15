package com.kuainiu.qt.data.biz.calc;

import com.kuainiu.qt.data.biz.bean.FuturesAccountOutBean;
import com.kuainiu.qt.data.biz.bean.SnapshotFuturesAccountOutBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/28
 * Time: 12:53 PM
 */
public interface FuturesAccountCalc {

    BigDecimal calcTotalFund(FuturesAccountOutBean account);

    BigDecimal calcTotalFund(SnapshotFuturesAccountOutBean account);

    BigDecimal calcTotalFund(List<SnapshotFuturesAccountOutBean> accountList);

}
