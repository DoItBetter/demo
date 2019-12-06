package com.kuainiu.qt.data.facade.response;

import com.kuainiu.qt.data.facade.bean.StkPositionFacadeBean;
import lombok.Data;

@Data
public class StkPositionResponse extends BaseDataResponse {

    private StkPositionFacadeBean data;
}
