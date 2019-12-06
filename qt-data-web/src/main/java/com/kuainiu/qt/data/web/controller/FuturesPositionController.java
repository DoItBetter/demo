package com.kuainiu.qt.data.web.controller;

import com.kuainiu.qt.data.facade.QtDataFuturesPositionFacade;
import com.kuainiu.qt.data.facade.request.FuturesPositionRequest;
import com.kuainiu.qt.data.facade.response.FuturesPositionResponse;
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

    @ApiOperation(httpMethod = "POST",value = "查询期货仓位")
    @RequestMapping(value="/findFuturesPosition",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "FuturesPositionPnlRequest ", paramType = "FuturesPositionPnlRequest")
    public FuturesPositionResponse findFuturesPosition(@RequestBody FuturesPositionRequest request) {
        return qtDataStkPositionFacade.findFuturesPosition(request);
    }
}
