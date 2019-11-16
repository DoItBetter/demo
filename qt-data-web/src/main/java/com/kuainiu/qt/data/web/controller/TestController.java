package com.kuainiu.qt.data.web.controller;

import com.kuainiu.qt.data.biz.PortfolioBiz;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioProcessorInBean;
import com.kuainiu.qt.data.biz.impl.PortfolioBizImpl;
import com.kuainiu.qt.data.exception.BizException;
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
    @ApiImplicitParam(name = "request", value = "request", required = true, dataType = "", paramType = "")
    public void qryPortfolio() throws BizException {
        PortfolioBiz biz = new PortfolioBizImpl();
        PortfolioProcessorInBean inBean = new PortfolioProcessorInBean();
        inBean.setPortfolioCode("PF000001");
        inBean.setForce(false);
        biz.recordSnapshot(inBean);
    }
}
