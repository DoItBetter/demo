package com.kuainiu.qt.data.web.controller;

import com.kuainiu.qt.data.facade.QtDataStkPositionFacade;
import com.kuainiu.qt.data.facade.request.StkPositionPnlRequest;
import com.kuainiu.qt.data.facade.response.StkPositionPnlResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stkPosition")
@Api(tags = "股票仓位")
public class StkPositionController {
    @Autowired
    QtDataStkPositionFacade qtDataStkPositionFacade;

    @ApiOperation(httpMethod = "POST",value = "查询股票仓位")
    @RequestMapping(value="/qryPnl",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "StkPositionRequest", paramType = "StkPositionRequest")
    public StkPositionPnlResponse qryPnl(@RequestBody StkPositionPnlRequest request) {
        return qtDataStkPositionFacade.getPnl(request);
    }
}
