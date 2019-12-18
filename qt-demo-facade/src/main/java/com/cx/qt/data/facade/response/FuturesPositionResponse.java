package com.cx.qt.data.facade.response;

import com.cx.qt.data.facade.bean.FuturesPositionFacadeBean;
import lombok.Data;

@Data
public class FuturesPositionResponse extends BaseDataResponse {
    private FuturesPositionFacadeBean data;
}
