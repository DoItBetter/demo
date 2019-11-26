package com.kuainiu.qt.data.service.http.request;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: Jixuan
 * Date: 2019-07-19
 * Time: 11:52
 */
@Data
public class PortfolioTimeRequest extends BaseDataHttpRequest {
    private String portfolio_key;

    private String strategy_key;
}