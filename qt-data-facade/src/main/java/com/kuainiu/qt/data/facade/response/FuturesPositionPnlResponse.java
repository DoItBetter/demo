package com.kuainiu.qt.data.facade.response;

import com.kuainiu.qt.data.facade.bean.FuturesPositionFacadeBean;
import lombok.Data;

@Data
public class FuturesPositionPnlResponse extends BaseDataResponse {
    private FuturesPositionFacadeBean data;
}
