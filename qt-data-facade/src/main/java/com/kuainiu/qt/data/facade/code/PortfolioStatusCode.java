package com.kuainiu.qt.data.facade.code;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/21
 * Time: 9:17 PM
 */
public enum PortfolioStatusCode {
    VALID("VALID", "可用"),
    INVALID("INVALID", "不可用"),
    ;

    @Getter
    private String code;

    @Setter
    private String msg;


    PortfolioStatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static PortfolioStatusCode getEnumByCode(String code) {
        if (code == null) {
            return null;
        }

        for (PortfolioStatusCode potfolioStatusCode: PortfolioStatusCode.values()) {
            if (potfolioStatusCode.getCode().equals(code)) {
                return potfolioStatusCode;
            }
        }
        return null;
    }
}
