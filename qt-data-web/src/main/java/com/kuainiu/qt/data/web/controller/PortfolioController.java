package com.kuainiu.qt.data.web.controller;

import com.kuainiu.qt.data.biz.PortfolioBiz;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioInformationRatioProcessorInBean;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioProcessorInBean;
import com.kuainiu.qt.data.biz.task.PortfolioInformationRatioProcessor;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.facade.QtDataSnapshotPortfolioFacade;
import com.kuainiu.qt.data.facade.request.*;
import com.kuainiu.qt.data.facade.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio")
@Api(tags = "投资组合")
public class PortfolioController {

    @Autowired
    QtDataSnapshotPortfolioFacade qtDataSnapshotPortfolioFacade;

    @ApiOperation(httpMethod = "POST",value = "查询投资组合")
    @RequestMapping(value="/qryPortfolio",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "PortfolioRequest", paramType = "PortfolioRequest")
    public PortfolioQryResponse qryPortfolio(@RequestBody PortfolioQryRequest request) {
        return qtDataSnapshotPortfolioFacade.qryPortfolio(request);
    }

    @ApiOperation(httpMethod = "POST",value = "查询方差")
    @RequestMapping(value="/qryStd",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "StdRequest", paramType = "StdRequest")
    public StdResponse qryStd(@RequestBody StdRequest request) {
        return qtDataSnapshotPortfolioFacade.qryPortfolioStd(request);
    }

    @ApiOperation(httpMethod = "POST",value = "查询InfoRatio")
    @RequestMapping(value="/qryInfoRatio",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "InfoRatioRequest", paramType = "InfoRatioRequest")
    public InfoRatioResponse qryInfoRatio(@RequestBody InfoRatioRequest request) {
        return qtDataSnapshotPortfolioFacade.qryInfoRatio(request);
    }

    @ApiOperation(httpMethod = "POST",value = "查询投资组合当日收益率")
    @RequestMapping(value="/qrySnapshotPortfolioList",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "PortfolioYieldRequest", paramType = "PortfolioYieldRequest")
    public SnapshotPortfolioListResponse qryPortfolioYield(@RequestBody PortfolioYieldRequest request) {
        return qtDataSnapshotPortfolioFacade.qrySnapshotPortfolioList(request);
    }

    @ApiOperation(httpMethod = "POST",value = "查询投资组合历史收益率")
    @RequestMapping(value="/qryLastRecordPerDay",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "PortfolioLastRecordPerDayRequest", paramType = "PortfolioLastRecordPerDayRequest")
    public SnapshotPortfolioListResponse qryLastRecordPerDay(@RequestBody PortfolioLastRecordPerDayRequest request) {
        return qtDataSnapshotPortfolioFacade.qryLastRecordPerDay(request);
    }

    @ApiOperation(httpMethod = "POST",value = "查询")
    @RequestMapping(value="/qryLastBeforeOpenMarket",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "PortfolioLastRecordPerDayRequest", paramType = "PortfolioLastRecordPerDayRequest")
    public SnapshotPortfolioResponse qryLastBeforeOpenMarket(@RequestBody SnapshotPortfolioRequest request) {
        return qtDataSnapshotPortfolioFacade.qryLastBeforeOpenMarket(request);
    }

    @Autowired
    PortfolioBiz portfolioBiz;

    @ApiOperation(httpMethod = "POST",value = "测试脚本")
    @RequestMapping(value="/portfolioProcessor",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = false, dataType = "", paramType = "")
    public void qryPortfolio() throws BizException {
        PortfolioProcessorInBean inBean = new PortfolioProcessorInBean();
        inBean.setPortfolioCode("PF000001");
        inBean.setForce(true);
        portfolioBiz.recordSnapshot(inBean);
    }

    @Autowired
    PortfolioInformationRatioProcessor portfolioInformationRatioProcessor;

    @ApiOperation(httpMethod = "POST",value = "测试Info脚本")
    @RequestMapping(value="/portfolioInformationRatioProcessor",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = false, dataType = "", paramType = "")
    public void portfolioInformationRatioProcessor() throws BizException {
        PortfolioInformationRatioProcessorInBean inBean = new PortfolioInformationRatioProcessorInBean();
        inBean.setForce(true);
        portfolioInformationRatioProcessor.recordPortfolio(inBean);
    }
}
