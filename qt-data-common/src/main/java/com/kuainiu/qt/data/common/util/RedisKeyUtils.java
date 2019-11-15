package com.kuainiu.qt.data.common.util;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/22
 * Time: 10:04 PM
 */
public class RedisKeyUtils {

    public static String getPortfolioRunDaysKey(String portfolioCode) {
        return RedisConst.KEY_PORTFOLIO_RUN_DAYS + portfolioCode;
    }

    public static String getPortfolioRunDaysListKey(String portfolioCode) {
        return RedisConst.KEY_PORTFOLIO_RUN_DAYS_LIST + portfolioCode;
    }

    public static String getIsTransTodayKey() {
        return RedisConst.KEY_IS_TRANS_TODAY;
    }
}
