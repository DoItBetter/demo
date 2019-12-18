package com.cx.qt.data.common.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalcUtils {
    public static boolean isZeroOrNull(BigDecimal a, BigDecimal b){
        return a == null || a.stripTrailingZeros().equals(BigDecimal.ZERO) || b == null || b.stripTrailingZeros().equals(BigDecimal.ZERO);
    }

    public static boolean isZeroOrNull(BigDecimal a){
        return a == null || a.stripTrailingZeros().equals(BigDecimal.ZERO);
    }

    public static boolean isNull(BigDecimal a, BigDecimal b){
        return a == null || b == null;
    }

    public static BigDecimal calcSubtract(BigDecimal a, BigDecimal b){
        if (isNull(a, b)){
            return BigDecimal.ZERO;
        }
        return a.subtract(b).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calcDivide(BigDecimal a, BigDecimal b){
        if (isZeroOrNull(a, b)){
            return BigDecimal.ZERO;
        }
        return a.divide(b, 4, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calcAdd(BigDecimal a, BigDecimal b){
        if (isNull(a, b)){
            return BigDecimal.ZERO;
        }
        return a.add(b).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calcMultiply(BigDecimal a, BigDecimal b){
        if (isZeroOrNull(a, b)){
            return BigDecimal.ZERO;
        }
        return a.multiply(b).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    //计算平方根
    public static BigDecimal calcSqrt(BigDecimal value){
        BigDecimal num2 = BigDecimal.valueOf(2);
        int precision = 100;
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal deviation = value;
        int cnt = 0;
        while (cnt < precision) {
            deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
            cnt++;
        }
        deviation = deviation.setScale(6, BigDecimal.ROUND_HALF_UP);
        return deviation;
    }
}
