package com.cx.qt.data.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PortfolioQryRequest extends BaseDataRequest {
    @ApiModelProperty(value = "策略组合编号")
    private String portfolioCode;

    @ApiModelProperty(value = "是否是实时计算")
    private boolean realTimeCalc = true;
}
