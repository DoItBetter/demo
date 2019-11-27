package com.kuainiu.qt.data.web.controller;

import com.kuainiu.qt.data.facade.QtDataFuturesPositionFacade;
import com.kuainiu.qt.data.facade.request.FuturesPositionPnlRequest;
import com.kuainiu.qt.data.facade.response.FuturesPositionPnlResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/futuresPosition")
@Api(tags = "期货仓位")
public class FuturesPositionController {
    @Autowired
    QtDataFuturesPositionFacade qtDataStkPositionFacade;

    @ApiOperation(httpMethod = "POST",value = "查询股票仓位")
    @RequestMapping(value="/qryPnl",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "FuturesPositionPnlRequest ", paramType = "FuturesPositionPnlRequest")
    public FuturesPositionPnlResponse qryPnl(@RequestBody FuturesPositionPnlRequest request) {
        return qtDataStkPositionFacade.getPnl(request);
    }
}
