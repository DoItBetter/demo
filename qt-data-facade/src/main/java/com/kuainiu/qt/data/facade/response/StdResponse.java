package com.kuainiu.qt.data.facade.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class StdResponse extends BaseDataResponse {
    @ApiModelProperty(value = "方差")
    private BigDecimal std;
}
