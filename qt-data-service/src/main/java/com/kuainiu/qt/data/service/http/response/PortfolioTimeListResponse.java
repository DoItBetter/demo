package com.kuainiu.qt.data.service.http.response;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: Jixuan
 * Date: 2019-07-19
 * Time: 18:35
 */
@Data
public class PortfolioTimeListResponse extends BaseDataHttpResponse {
    private JSONArray data;
}
