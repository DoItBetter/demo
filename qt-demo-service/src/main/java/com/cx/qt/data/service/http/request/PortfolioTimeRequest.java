package com.cx.qt.demo.service.http.request;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019-07-19
 * Time: 11:52
 */
@Data
public class PortfolioTimeRequest extends BasedemoHttpRequest {
    private String portfolio_key;

    private String strategy_key;
}