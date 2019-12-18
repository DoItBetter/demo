package com.cx.qt.demo.web.controller;

import com.cx.qt.demo.biz.PortfolioBiz;
import com.cx.qt.demo.biz.SnapshotPortfolioBiz;
import com.cx.qt.demo.biz.bean.processor.PortfolioInformationRatioProcessorInBean;
import com.cx.qt.demo.biz.bean.processor.PortfolioProcessorInBean;
import com.cx.qt.demo.exception.BizException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(tags = "测试")
public class TestController {
    @Autowired
    PortfolioBiz portfolioBiz;

    @ApiOperation(httpMethod = "POST",value = "测试脚本")
    @RequestMapping(value="/portfolioProcessor",produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "request", value = "request", required = false, dataType = "", paramType = "")
    public void qryPortfolio() throws BizException {
        PortfolioProcessorInBean inBean = new PortfolioProcessorInBean();
        inBean.setForce(true);
        portfolioBiz.recordSnapshot(inBean);
    }
}
