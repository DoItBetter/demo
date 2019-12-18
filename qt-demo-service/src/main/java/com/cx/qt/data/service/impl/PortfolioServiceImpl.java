package com.cx.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.cx.qt.data.exception.ServiceException;
import com.cx.qt.data.service.PortfolioService;
import com.cx.qt.data.util.SerBeanUtils;
import com.cx.qt.data.facade.code.QtDataRspCode;
import com.cx.qt.data.service.bean.trans.PortfolioQrySerBean;
import com.cx.qt.data.service.bean.trans.PortfolioReqSerBean;
import com.cx.qt.data.service.bean.trans.PortfolioSerBean;
import com.cx.qt.trans.facade.code.QtTransRspCode;
import com.cx.qt.trans.facade.request.PortfolioFindAllRequest;
import com.cx.qt.trans.facade.request.PortfolioQryRequest;
import com.cx.qt.trans.facade.response.PortfolioFindAllResponse;
import com.cx.qt.trans.facade.response.PortfolioQryResponse;
import com.cx.qt.trans.facade.trans.QtTransPortfolioQryFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {
    private final String QT_TRANS_RESPONSE_SUCC = QtTransRspCode.SUCCESS.getCode();

    @Reference
    QtTransPortfolioQryFacade qtTransPortfolioQryFacade;

    @Override
    public List<PortfolioSerBean> findAll(PortfolioReqSerBean reqSerBean) throws ServiceException {
        List<PortfolioSerBean> portfolioSerBeanList = new ArrayList<>();
        PortfolioFindAllRequest request = SerBeanUtils.buildFindAllRequest(reqSerBean);
        log.info("find all request ={}", JSON.toJSONString(request));
        PortfolioFindAllResponse response = new PortfolioFindAllResponse();
        try {
            response = qtTransPortfolioQryFacade.qryAll(request);
            log.info("find all response ={}", JSON.toJSONString(response));
        } catch (RpcException e){
            log.error("trans find portfolio all fail rpc", e);
            throw new ServiceException(QtDataRspCode.ERR_SYS_RPC);
        } catch (Exception e){
            log.error("trans fail", e);
            throw new ServiceException(QtDataRspCode.ERR_QRY_TRANS_PORTFOLIO_ALL_FAIL, e.getMessage());
        }
        if (!response.getCode().equals(QT_TRANS_RESPONSE_SUCC)) {
            throw new ServiceException(response.getMsg());
        }
        portfolioSerBeanList = SerBeanUtils.buildPortfolioSerBeanList(response);
        return portfolioSerBeanList;
    }

    @Override
    public PortfolioQrySerBean qryPortfolio(PortfolioReqSerBean reqSerBean) throws ServiceException {
        PortfolioQryRequest request = SerBeanUtils.buildPortfolioQryRequest(reqSerBean);
        log.info("qry portfolio info request ={}", JSON.toJSONString(request));
        PortfolioQryResponse response = new PortfolioQryResponse();
        try {
            response = qtTransPortfolioQryFacade.qryPortfolio(request);
            log.info("qry portfolio info response ={} ", JSON.toJSONString(response));
        } catch (RpcException e){
            log.error("trans qry portfolio info fail rpc", e);
            throw new ServiceException(QtDataRspCode.ERR_SYS_RPC);
        } catch (Exception e){
            log.error("trans qry portfolio info fail ", e);
            throw new ServiceException(QtDataRspCode.ERR_QRY_TRANS_PORTFOLIO_FAIL, e.getMessage());
        }
        if (!response.getCode().equals(QT_TRANS_RESPONSE_SUCC)) {
            throw new ServiceException(response.getMsg());
        }
        return SerBeanUtils.buildPortfolioSerBean(response);
    }
}