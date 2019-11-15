package com.kuainiu.qt.data.biz.bean;

import com.kuainiu.qt.framework.common.bean.bizbean.BaseInBean;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class StdInBean extends BaseInBean {
    private BigDecimal std;
}
