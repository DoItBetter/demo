package com.kuainiu.qt.data.facade.response;

import com.kuainiu.qt.data.facade.bean.FuturesPositionFacadeBean;
import lombok.Data;

@Data
public class FuturesPositionResponse extends BaseDataResponse {
    private FuturesPositionFacadeBean data;
}
