package com.kuainiu.qt.data.biz.bean.processor;

import com.kuainiu.qt.framework.common.bean.bizbean.BaseInBean;
import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/27
 * Time: 1:07 PM
 */
@Data
public class PortfolioProcessorInBean extends BaseInBean {

    private boolean isForce;

    private String portfolioCode;

    private Date startDate;
}
