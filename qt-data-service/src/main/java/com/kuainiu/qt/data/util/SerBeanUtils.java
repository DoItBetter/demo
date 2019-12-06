package com.kuainiu.qt.data.util;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.dal.entity.SnapshotPortfolio;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.bean.*;
import com.kuainiu.qt.data.service.bean.aidc.RmReqSerBean;
import com.kuainiu.qt.data.service.bean.aidc.RmSerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioQrySerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioSerBean;
import com.kuainiu.qt.data.service.http.request.StockEarningRate300Request;
import com.kuainiu.qt.data.service.http.response.StockEarningRate300Response;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.trans.facade.request.PortfolioFindAllRequest;
import com.kuainiu.qt.trans.facade.request.PortfolioQryRequest;
import com.kuainiu.qt.trans.facade.response.PortfolioFindAllResponse;
import com.kuainiu.qt.trans.facade.response.PortfolioQryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SerBeanUtils {
    public static List<SnapshotPortfolioSerBean> buildSnapshotPortfolioSerBeanList(List<SnapshotPortfolio> snapshotPortfolioList) {
        List<SnapshotPortfolioSerBean> snapshotPortfolioSerBeanList = new ArrayList<>();

        snapshotPortfolioList.forEach(snapshotPortfolio -> {
            SnapshotPortfolioSerBean serBean = buildSnapshotPortfolioSerBean(snapshotPortfolio);
            snapshotPortfolioSerBeanList.add(serBean);
        });

        return snapshotPortfolioSerBeanList;
    }

    public static SnapshotPortfolioSerBean buildSnapshotPortfolioSerBean(SnapshotPortfolio snapshotPortfolio) {
        SnapshotPortfolioSerBean serBean = new SnapshotPortfolioSerBean();
        BeanMapUtils.map(snapshotPortfolio, serBean);
        return serBean;
    }

    public static SnapshotPortfolio buildSnapshotPortfolio(SnapshotPortfolioSerBean serBean) {
        SnapshotPortfolio snapshotPortfolio = new SnapshotPortfolio();
        if (serBean != null) {
            BeanMapUtils.map(serBean, snapshotPortfolio);
        }
        return snapshotPortfolio;
    }

    public static SnapshotPortfolioSerBean buildSnapshotPortfolio(SnapshotPortfolio snapshotPortfolio) throws ServiceException {
        SnapshotPortfolioSerBean serBean = new SnapshotPortfolioSerBean();
        BeanMapUtils.map(snapshotPortfolio, serBean);
        try {
            List<SnapshotFuturesAccountSerBean> futuresAccountList = BeanMapUtils.mapAsList(snapshotPortfolio.getFuturesAccountList(), SnapshotFuturesAccountSerBean.class);
            List<SnapshotStkAccountSerBean> stkAccountList = BeanMapUtils.mapAsList(snapshotPortfolio.getStkAccountList(), SnapshotStkAccountSerBean.class);
            serBean.setFuturesAccountList(futuresAccountList);
            serBean.setStkAccountList(stkAccountList);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ServiceException(QtDataRspCode.ERR_COPY_LIST);
        }

        return serBean;
    }

    public static PortfolioQryRequest buildPortfolioQryRequest(PortfolioReqSerBean reqSerBean) {
        PortfolioQryRequest request = new PortfolioQryRequest();
        BeanMapUtils.map(reqSerBean, request);
        return request;
    }

    public static PortfolioQrySerBean buildPortfolioSerBean(PortfolioQryResponse response) throws ServiceException {
        PortfolioQrySerBean serBean = new PortfolioQrySerBean();
        BeanMapUtils.map(response, serBean);
        List<StkPositionSerBean> stkPositionList = new ArrayList<>();
        List<FuturesPositionSerBean> futuresPositions = new ArrayList<>();
        List<CashflowSerBean> cashflowList = new ArrayList<>();
        List<StkAccountSerBean> stkAccountList = new ArrayList<>();
        List<FuturesAccountSerBean> futuresAccountList = new ArrayList<>();
        try {
            response.getStkPositions().forEach(stkPositionFacadeBean -> {
                StkPositionSerBean stkPositionSerBean = new StkPositionSerBean();
                BeanMapUtils.map(stkPositionFacadeBean, stkPositionSerBean);
                if (stkPositionFacadeBean.getStkFee() != null) {
                    StkAssetDetailFeeSerBean stkAssetDetailFeeSerBean = new StkAssetDetailFeeSerBean();
                    BeanMapUtils.map(stkPositionFacadeBean.getStkFee(), stkAssetDetailFeeSerBean);
                    stkPositionSerBean.setStkFee(stkAssetDetailFeeSerBean);
                }
                stkPositionList.add(stkPositionSerBean);
            });
            serBean.setStkPositionList(stkPositionList);
            futuresPositions = BeanMapUtils.mapAsList(response.getFuturesPositions(), FuturesPositionSerBean.class);
            serBean.setFuturesPositionList(futuresPositions);
            cashflowList = BeanMapUtils.mapAsList(response.getCashflowList(), CashflowSerBean.class);
            serBean.setCashflowList(cashflowList);
            response.getStkAccountList().forEach(stkAccountFacadeBean -> {
                StkAccountSerBean stkAccountSerBean = new StkAccountSerBean();
                BeanMapUtils.map(stkAccountFacadeBean, stkAccountSerBean);
                StkFeeSerBean feeSerBean = new StkFeeSerBean();
                BeanMapUtils.map(stkAccountFacadeBean.getTransactionCost(), feeSerBean);
                stkAccountSerBean.setTransactionCost(feeSerBean);
                stkAccountList.add(stkAccountSerBean);
            });
            serBean.setStkAccountList(stkAccountList);
            futuresAccountList = BeanMapUtils.mapAsList(response.getFuturesAccountList(), FuturesAccountSerBean.class);
            serBean.setFuturesAccountList(futuresAccountList);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("[copy list fail] {}", JSON.toJSONString(stkPositionList));
            throw new ServiceException(QtDataRspCode.ERR_SYS_ERROR);
        }
        return serBean;
    }

    public static PortfolioFindAllRequest buildFindAllRequest(PortfolioReqSerBean reqSerBean) {
        PortfolioFindAllRequest request = new PortfolioFindAllRequest();
        BeanMapUtils.map(reqSerBean, request);
        return request;
    }

    public static List<PortfolioSerBean> buildPortfolioSerBeanList(PortfolioFindAllResponse response) throws ServiceException {
        List<PortfolioSerBean> serBeanList = new ArrayList<>();
        try {
            serBeanList = BeanMapUtils.mapAsList(response.getData(), PortfolioSerBean.class);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("[copy list fail] {}", JSON.toJSONString(serBeanList));
            throw new ServiceException(QtDataRspCode.ERR_SYS_ERROR);
        }
        return serBeanList;
    }

    public static StockEarningRate300Request buildRmRequest(RmReqSerBean reqSerBean) {
        StockEarningRate300Request request = new StockEarningRate300Request();
        BeanMapUtils.map(reqSerBean, request);
        return request;
    }

    public static RmSerBean buildRmSerBean(StockEarningRate300Response earningResponse) {
        RmSerBean rmSerBean = new RmSerBean();
        rmSerBean.setData(earningResponse.getData().getData());
        return rmSerBean;
    }
}
