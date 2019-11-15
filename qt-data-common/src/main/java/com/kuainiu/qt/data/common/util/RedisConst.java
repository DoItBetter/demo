package com.kuainiu.qt.data.common.util;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/20
 * Time: 2:40 PM
 */
public final class RedisConst {
    public final static long EXPIRE_MINUTE = 60;

    public final static long EXPIRE_FIVE_MINUTE = 5 * 60;

    public final static long EXPIRE_HOUR = 3600;

    public final static long EXPIRE_DAY = 3600 * 24;

    public final static String KEY_FUTURES_ACCOUNT = "futures_account_";

    public final static String KEY_FUTURES_POSITION = "futures_position_";

    public final static String KEY_PORTFOLIO_RUN_DAYS_NUM = "portfolio_run_days_num_";

    public final static String KEY_PORTFOLIO_RUN_DAYS = "portfolio_run_days_";

    public final static String KEY_FUTURES_PREV_PRICE = "futures_prev_price_";

    public final static String KEY_FUTURES_PRE_SETTLEMENT = "futures_pres_settlement_";

    public final static String KEY_FUTURES_PRE_SETTLEMENT_YESTERDAY = "futures_pres_settlement_yesterday_";

    public final static String KEY_FUTURES_SETTLE_PRICE_YESTERDAY = "futures_pres_settlement_yesterday_";

    public final static String KEY_FUTURES_SETTLE_PRICE_BEFORE_YESTERDAY = "futures_settle_price_before_yesterday_";

    public final static String KEY_FUTURES_LAST_SNAPSHOT = "futures_last_snapshot_";

    public static final String KEY_PORTFOLIO_RUN_DAYS_LIST = "portfolio_run_days_list_";
    /**
     * 今天是否是交易日
     */
    public static final String KEY_IS_TRANS_TODAY = "is_trans_today";

    /**
     * 股票今日买
     */
    public final static String KEY_STK_TODAY_BUY_ASSET_AMOUNT = "stk_today_buy_asset_amount_";

    /**
     * 股票今日卖
     */
    public final static String KEY_STK_TODAY_SELL_ASSET_AMOUNT = "stk_today_sell_asset_amount_";

    public final static String KEY_REAL_ACCOUNT_CODE = "real_account_code_";


    public final static String KEY_FUTURES_ASSET_TRANS_COST_TODAY = "futures_asset_trans_cost_today_";

    public final static String KEY_FUTURES_REAL_ACCOUNT_OPEN_QTY = "futures_real_account_open_qty_";
    /**
     * 涨停价
     */
    public final static String KEY_STK_LIMIT_UP = "stk_limit_up_";

    public final static String KEY_MARGIN_FLOW_BY_TRANS = "margin_flow_by_trans_";

    public final static String KEY_REAL_ACCOUNT_ASSET_LOG_LIST = "real_account_asset_log_list";

    public final static String KEY_ACCOUNT_ASSET_LOG_LIST = "account_asset_log_list";

    public final static String KEY_EACH_ACCOUNT_MARGIN = "each_account_margin";

    public final static String KEY_FUTURES_TRANS_FEE = "futures_trans_fee";

    public final static String KEY_FUTURES_ASSET_FLOW_BY_TRANS = "futures_asset_flow_by_trans_";

    public final static String KEY_FUTURES_ASSET_DETAIL_CALC_BEAN = "futures_asset_calc_bean";

    /**
     * 期货涨停价
     */
    public final static String KEY_FUTURES_LIMIT_UP = "futures_limit_up_";


    public final static String LOCK_FUTURES_NOTIFY = "lock_futures_notify_";

    public final static String LOCK_STK_NOTIFY = "lock_stk_notify_";

    public final static String LOCK_ACCOUNT= "lock_account_";

    public final static String LOCK_REAL_ACCOUNT= "lock_real_account_";

    public final static String LOCK_BALANCE_FLOW_BY_STK_ORDER= "lock_balance_flow_bt_stk_order_";

    /**
     * 期货资产锁
     */
    public final static String LOCK_FUTURES_ASSET = "lock_futures_asset";
    public final static String KEY_ASSET_NAME = "asset_name_";
}
