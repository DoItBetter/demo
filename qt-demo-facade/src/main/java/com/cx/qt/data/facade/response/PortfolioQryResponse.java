package com.cx.qt.data.facade.response;

import com.cx.qt.data.facade.bean.PortfolioInfoFacadeBean;
import lombok.Data;

@Data
public class PortfolioQryResponse extends BaseDataResponse {

    private PortfolioInfoFacadeBean data;
}
