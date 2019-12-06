package com.kuainiu.qt.data.biz.facade.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.biz.PortfolioQryBiz;
import com.kuainiu.qt.data.biz.SnapshotPortfolioBiz;
import com.kuainiu.qt.data.biz.bean.PortfolioInBean;
import com.kuainiu.qt.data.biz.bean.PortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.facade.QtDataSnapshotPortfolioFacade;
import com.kuainiu.qt.data.facade.request.*;
import com.kuainiu.qt.data.facade.response.*;
import com.kuainiu.qt.data.param.ParamCheckHandle;
import com.kuainiu.qt.data.util.BizBeanUtils;
import com.kuainiu.qt.data.util.BizResponseUtils;
import com.kuainiu.qt.data.util.PortfolioBizUtils;
import com.kuainiu.qt.data.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Service
public class QtDataSnapshotPortfolioFacadeImpl implements QtDataSnapshotPortfolioFacade {
    @Autowired
    PortfolioQryBiz portfolioQryBiz;

    @Autowired
    SnapshotPortfolioBiz snapshotPortfolioBiz;

    @Override
    public PortfolioQryResponse qryPortfolio(PortfolioQryRequest request) {
        log.info("[qry portfolio]request={}", JSON.toJSONString(request));
        PortfolioQryResponse response = new PortfolioQryResponse();
        try {
            ParamCheckHandle.checkPortfolioQryRequest(request);
            PortfolioInBean inBean = PortfolioBizUtils.buildPortfolioInBean(request);
            PortfolioOutBean outBean = portfolioQryBiz.qryPortfolio(inBean);
            response = PortfolioBizUtils.buildPortfolioQryResponse(outBean);
            ResponseUtils.success(response);
        } catch (Exception e) {
            log.error("[qrtPortfolio fail]", e);
            ResponseUtils.sysError(response, e);
        }
        log.info("[qrtPortfolio response]", JSON.toJSONString(response));
        return response;
    }

    @Override
    public StdResponse qryPortfolioStd(StdRequest request) {
        log.info("[qrtPortfolioStd request]", JSON.toJSONString(request));
        StdResponse response = new StdResponse();
        try {
            ParamCheckHandle.checkPortfolioStdQryRequest(request);
            PortfolioInBean inBean = PortfolioBizUtils.buildPortfolioInBean(request);
            PortfolioOutBean outBean = portfolioQryBiz.qryPortfolioStd(inBean);
            response.setStd(outBean.getStd());
            ResponseUtils.success(response);
        } catch (Exception e) {
            log.error("[qrtPortfolioStd fail]", e);
            ResponseUtils.sysError(response, e);
        }
        log.info("[qrtPortfolioStd response]", JSON.toJSONString(response));
        return response;
    }

    @Override
    public InfoRatioResponse qryInfoRatio(InfoRatioRequest request) {
        log.info("[qryPortfolioInfRatio request] {}", JSON.toJSONString(request));
        InfoRatioResponse response = new InfoRatioResponse();
        try {
            ParamCheckHandle.checkInfoRatioQryRequest(request);
            SnapshotPortfolioInBean inBean = BizBeanUtils.buildSnapshotPortfolioInBean(request);
            SnapshotPortfolioOutBean serBean = portfolioQryBiz.qryInfoRatio(inBean);
            response.setInformationRatio(serBean.getInformationRatio());
            ResponseUtils.success(response);
        } catch (Exception e) {
            ResponseUtils.sysError(response, e);
        }
        log.info("[qryPortfolioInfRatio response]", JSON.toJSONString(response));
        return response;
    }

    @Override
    public SnapshotPortfolioListResponse qrySnapshotPortfolioList(PortfolioYieldRequest request) {
        log.info("[qrySnapshotPortfolioList request] {}", JSON.toJSONString(request));
        SnapshotPortfolioListResponse response = new SnapshotPortfolioListResponse();
        try {
            ParamCheckHandle.checkQrySnapshotPFListRequest(request);
            SnapshotPortfolioInBean inBean = BizBeanUtils.buildSnapshotPortfolioInBean(request);
            List<SnapshotPortfolioOutBean> outBeanList = portfolioQryBiz.qrySnapshotPortfolioList(inBean);
            BizResponseUtils.buildQtTransSnapshotPortfolioQryResponse(outBeanList, response);
            ResponseUtils.success(response);
        } catch (Exception e) {
            ResponseUtils.sysError(response, e);
        }
        log.info("[qrySnapshotPortfolioList response] {}", JSON.toJSONString(response));
        return response;
    }

    @Override
    public SnapshotPortfolioListResponse qryLastRecordPerDay(PortfolioLastRecordPerDayRequest request) {
        log.info("[qryLastRecordPerDay request] {}", JSON.toJSONString(request));
        SnapshotPortfolioListResponse response = new SnapshotPortfolioListResponse();
        try {
            ParamCheckHandle.checkQryLastRecordperDay(request);
            SnapshotPortfolioInBean inBean = BizBeanUtils.buildSnapshotPortfolioInBean(request);
            List<SnapshotPortfolioOutBean> outBeanList = portfolioQryBiz.qryLastRecordPerDay(inBean);
            BizResponseUtils.buildQtTransSnapshotPortfolioQryResponse(outBeanList, response);
            ResponseUtils.success(response);
        } catch (Exception e) {
            ResponseUtils.sysError(response, e);
        }
        log.info("[qryLastRecordPerDay response] {}", JSON.toJSONString(response));
        return response;
    }

    @Override
    public SnapshotPortfolioResponse findByPFCodeBelongTimeAndErrorFlag(SnapshotPortfolioRequest request) {
        log.info("[qryLastRecordPerDay request] {}", JSON.toJSONString(request));
        SnapshotPortfolioResponse response = new SnapshotPortfolioResponse();
        try {
            ParamCheckHandle.checkQryLastBeforeOpenMarketRequest(request);
            SnapshotPortfolioInBean inBean = BizBeanUtils.buildSnapshotPortfolioInBean(request);
            SnapshotPortfolioOutBean outBean = snapshotPortfolioBiz.findByPFCodeBelongTimeAndErrorFlag(inBean);
            response = BizResponseUtils.buildSnapshotPortfolioResponse(outBean);
            ResponseUtils.success(response);
        } catch (Exception e) {
            ResponseUtils.sysError(response, e);
        }
        log.info("[findByPFCodeBelongTimeAndErrorFlag response] {}", JSON.toJSONString(response));
        return response;
    }
}
