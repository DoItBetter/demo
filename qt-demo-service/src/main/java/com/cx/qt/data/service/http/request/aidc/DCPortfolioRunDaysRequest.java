package com.cx.qt.data.service.http.request.aidc;

import com.cx.qt.data.service.http.request.BaseDataHttpRequest;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/8/6
 * Time: 4:20 PM
 */
@Data
public class DCPortfolioRunDaysRequest extends BaseDataHttpRequest {
    private String portfolio_key;

    private String strategy_key;
}
