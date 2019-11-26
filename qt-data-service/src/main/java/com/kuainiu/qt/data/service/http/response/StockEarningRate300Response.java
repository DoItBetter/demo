package com.kuainiu.qt.data.service.http.response;

import com.kuainiu.qt.data.service.http.bean.StockEarningRate300HttpBean;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: Jixuan
 * Date: 2019-07-19
 * Time: 14:19
 */
@Data
public class StockEarningRate300Response extends BaseDataHttpResponse{
    private StockEarningRate300HttpBean data;
}
