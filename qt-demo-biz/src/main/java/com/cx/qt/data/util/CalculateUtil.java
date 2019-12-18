package com.cx.qt.data.util;

import com.cx.qt.framework.common.util.CommonConstant;

import java.math.BigDecimal;

public class CalculateUtil {
    public static Boolean isZero(BigDecimal item) {
        return CommonConstant.STRING_ZERO.equals(item.stripTrailingZeros().toPlainString());
    }
}
