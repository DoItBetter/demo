package com.kuainiu.qt.data.service.impl;

import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.PortfolioService;
import com.kuainiu.qt.data.service.bean.PortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.PortfolioSerBean;
import com.kuainiu.qt.data.util.SerBeanUtils;
import com.kuainiu.qt.trans.facade.request.PortfolioFindAllRequest;
import com.kuainiu.qt.trans.facade.request.PortfolioQryRequest;
import com.kuainiu.qt.trans.facade.response.PortfolioFindAllResponse;
import com.kuainiu.qt.trans.facade.response.PortfolioQryResponse;
import com.kuainiu.qt.trans.facade.trans.QtTransPortfolioQryFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/5/21
 * Time: 6:47 PM
 */
@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {
    @Reference
    QtTransPortfolioQryFacade qtTransPortfolioQryFacade;

    @Override
    public List<PortfolioSerBean> findAll(PortfolioSerBean serBean) throws ServiceException {
        List<PortfolioSerBean> portfolioSerBeanList = new ArrayList<>();
        PortfolioFindAllRequest request = SerBeanUtils.buildFindAllRequest(serBean);
        PortfolioFindAllResponse response = qtTransPortfolioQryFacade.qryAll(request);
        portfolioSerBeanList = SerBeanUtils.buildPortfolioSerBeanList(response);
        return portfolioSerBeanList;
    }

    @Override
    public PortfolioSerBean qryPortfolio(PortfolioReqSerBean reqSerBean) throws ServiceException {
        PortfolioQryRequest request = SerBeanUtils.buildPortfolioQryRequest(reqSerBean);
        log.info("qry portfolio info request : " + request);
        PortfolioQryResponse response = new PortfolioQryResponse();
        try {
            response = qtTransPortfolioQryFacade.qryPortfolio(request);
            log.info("qry portfolio info response : " + response);
        } catch (RpcException e){
            log.error("trans qry portfolio info fail rpc", e);
            throw new ServiceException(QtDataRspCode.SYS_TIMEOUT);
        } catch (Exception e){
            log.error("trans fail", e);
        }
        return SerBeanUtils.buildPortfolioSerBean(response);
    }
}