package com.kuainiu.qt.data.service.bean.trans;

import com.kuainiu.qt.data.service.bean.BaseDataSerBean;
import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/5/21
 * Time: 8:30 PM
 */
@Data
public class PortfolioSerBean extends BaseDataSerBean {

    private String portfolioCode;

    private String portfolioName;

    private String accessToken;

    private Date startDate;

    private String status;

    private String channelCode;
}
