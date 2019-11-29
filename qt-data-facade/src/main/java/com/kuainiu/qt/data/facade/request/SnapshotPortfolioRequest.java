package com.kuainiu.qt.data.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class SnapshotPortfolioRequest extends BaseDataRequest {
    @ApiModelProperty(value = "策略组合编号")
    private String portfolioCode;
    @ApiModelProperty(value = "时间节点")
    private Date endBelongTime;
    @ApiModelProperty(value = "错误标志")
    private String errorFlag;
}
