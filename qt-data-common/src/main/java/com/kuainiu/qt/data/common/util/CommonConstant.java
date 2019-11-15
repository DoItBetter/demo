package com.kuainiu.qt.data.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/5/18
 * Time: 2:41 PM
 */
public class CommonConstant {
    /** 当前系统编号 */
    public static final String SYSTEM_NO2 = "30045";


    public final static String ENCODE = "UTF-8";

    /** 逗号: , */
    public static final String COMMA = ",";

    /** 零 */
    public static final int ZERO = 0;

    public static final Integer INTEGER_ZERO = 0;

    public static final int INVALID_NUMBER = -1;

    /** 左括号 */
    public static final String LEFT_BRACKET = "[";

    /** 右括号 */
    public static final String RIGHT_BRACKET = "]";

    /** 冒号 */
    public static final String COLON = ":";

    /** 默认编号 */
    public static final String DEFAULT_SEQNO = "-";

    /** 默认分页大小 */
    public static final int DEFAULT_BATCH_SIZE = 1000;

    /** 异常最长限制 */
    public static final int MAX_EXCEPTION_LEN = 1024;

    public static final int INT_ZERO = 0;

    public static final int INT_ONE = 1;

    public static final int INT_NEGATIVE_ONE = -1;

    public static final String DATEFORMAT = "yyyyMMdd";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DDHHMM = "dd日HH时mm分";

    public static final String STRING_ZERO = "0";

    public static final String DOUBLE_ZERO = "00";

    public static final String STRING_ONE = "1";

    public static final String DATEFORMAT_START = "yyyy-MM-dd 00:00:00";

    public static final String DATEFORMAT_END = "yyyy-MM-dd 23:59:59";

    public static final String DATEFORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static final String STOCK_DAY_AM_PREPARETIME = "09:00:00";

    public static final String STOCK_DAY_AM_STARTTIME = "09:30:00";

    public static final String STOCK_DAY_AM_ENDTIME = "11:30:00";

    public static final String STOCK_DAY_PM_STARTTIME = "13:00:00";

    public static final String STOCK_DAY_PM_ENDTIME = "15:00:00";
    //脚本跑到三点半
    public static final String STOCK_DAY_PM_FINESHTIME = "15:30:01";

    public static final String SUCCESS = "1000";

    public static final long LONG_ZERO = 0;

    public static final String SEQ_RES = "^[0-9, A-Z]*$";

    public static final int SCALE_DEFAULT = 6;

    public static final RoundingMode ROUND_DEFAULT = RoundingMode.HALF_DOWN;

    public static final BigDecimal BIG_DECIMAL_ZERO = new BigDecimal(0.000000);

    public static final BigDecimal BIGDECIMAL_MINUS_ONE = new BigDecimal(-1);

    public static final Integer TRANS_DAYS_A_YEAR = 252;

    public static final String DB_ORDER_ASC = "asc";

    public static final String DB_ORDER_DESC = "desc";

    public static final String ONE_STR = "1";

    public static final Integer ONE = 1;

    public static String SUCC = "SUCC";

    public static String FIX_ORDER_PRE = "fix_";
}
