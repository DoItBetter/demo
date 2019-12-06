package com.kuainiu.qt.data.facade.response;

import com.kuainiu.qt.data.facade.bean.PortfolioInfoFacadeBean;
import lombok.Data;

@Data
public class PortfolioQryResponse extends BaseDataResponse {

    private PortfolioInfoFacadeBean data;
}
