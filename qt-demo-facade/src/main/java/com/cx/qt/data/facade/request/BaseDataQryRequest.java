package com.cx.qt.data.facade.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
public class BaseDataQryRequest extends BaseDataRequest{
    Integer pageNo = 0;

    Integer pageSize = 1000;
}
