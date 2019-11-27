package com.kuainiu.qt.data.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class  StkPositionPnlRequest extends BaseDataRequest {
    @ApiModelProperty(value = "策略编号")
    private String strategyCode;
    @ApiModelProperty(value = "合约代码")
    private String assetNo;

    private Date endBelongTime;
}
