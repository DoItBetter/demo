package com.cx.qt.data.facade.response;

import com.cx.qt.data.facade.bean.StkPositionFacadeBean;
import lombok.Data;

@Data
public class StkPositionResponse extends BaseDataResponse {

    private StkPositionFacadeBean data;
}
