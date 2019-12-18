package com.cx.qt.data.facade.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@ApiModel
public class InfoRatioResponse extends BaseDataResponse{
    @ApiModelProperty(value = "informationRatio值")
    private BigDecimal informationRatio;
}
