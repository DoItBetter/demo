package com.kuainiu.qt.data.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class PortfolioYieldRequest extends BaseDataQryRequest{
    @ApiModelProperty(value = "策略组合编号")
    private String portfolioCode;
    @ApiModelProperty(value = "开始时间")
    private Date startBelongTime;
    @ApiModelProperty(value = "结束时间")
    private Date endBelongTime;
}
