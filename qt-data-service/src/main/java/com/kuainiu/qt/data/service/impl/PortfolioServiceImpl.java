package com.kuainiu.qt.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.PortfolioService;
import com.kuainiu.qt.data.service.bean.trans.PortfolioQrySerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioSerBean;
import com.kuainiu.qt.data.util.SerBeanUtils;
import com.kuainiu.qt.trans.facade.code.QtTransRspCode;
import com.kuainiu.qt.trans.facade.request.PortfolioFindAllRequest;
import com.kuainiu.qt.trans.facade.request.PortfolioQryRequest;
import com.kuainiu.qt.trans.facade.response.PortfolioFindAllResponse;
import com.kuainiu.qt.trans.facade.response.PortfolioQryResponse;
import com.kuainiu.qt.trans.facade.trans.QtTransPortfolioQryFacade;
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