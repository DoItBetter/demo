package com.cx.qt.data.facade.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class SnapshotPortfolioFacadeBean extends BaseTransFacadeBean {
    @ApiModelProperty(value = "rm")
    private BigDecimal realtimeReturns;
    @ApiModelProperty(value = "rp")
    private BigDecimal baseRealtimeReturns;
    @ApiModelProperty(value = "所属时间")
    private Date belongTime;
    @ApiModelProperty(value = "所属日期")
    private Date belongDate;
}
