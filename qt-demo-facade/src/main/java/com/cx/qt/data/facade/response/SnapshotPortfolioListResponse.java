package com.cx.qt.data.facade.response;

import com.cx.qt.data.facade.bean.SnapshotPortfolioFacadeBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class SnapshotPortfolioListResponse extends BaseDataResponse {
    @ApiModelProperty(value = "查询结果")
    List<SnapshotPortfolioFacadeBean> data;
}
