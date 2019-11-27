package com.kuainiu.qt.data.biz.calc;

import com.kuainiu.qt.data.service.bean.FuturesAccountSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotFuturesAccountSerBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/28
 * Time: 12:53 PM
 */
public interface FuturesAccountCalc {

    BigDecimal calcTotalFund(FuturesAccountSerBean account);

    BigDecimal calcTotalFund(SnapshotFuturesAccountSerBean account);

    BigDecimal calcTotalFund(List<SnapshotFuturesAccountSerBean> accountList);

}
