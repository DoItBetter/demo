package com.cx.qt.data.facade.code;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/9/23
 * Time: 7:59 PM
 */
public enum StkFeeTypeCode {
    FEE_TYPE_TRADE("TRADE", "交易手续费"),
    FEE_TYPE_TRANSFER("TRANSFER", "过户费"),
    FEE_TYPE_YHS("YHS", "印花税"),
    FEE_TYPE_YJ("YJ", "佣金"),
    ;

    @Getter
    private String code;

    @Setter
    private String msg;


    StkFeeTypeCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static StkFeeTypeCode getEnumByCode(String code) {
        if (code == null) {
            return null;
        }

        for (StkFeeTypeCode stkFeeTypeCode: StkFeeTypeCode.values()) {
            if (stkFeeTypeCode.getCode().equals(code)) {
                return stkFeeTypeCode;
            }
        }
        return null;
    }
}
