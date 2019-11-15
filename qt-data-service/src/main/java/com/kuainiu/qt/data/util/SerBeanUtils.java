package com.kuainiu.qt.data.util;

import com.kuainiu.qt.data.dal.entity.SnapshotPortfolio;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.bean.*;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.trans.facade.request.PortfolioQryRequest;
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
        BeanMapUtils.map(serBean, snapshotPortfolio);
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

    public static PortfolioSerBean buildPortfolioSerBean(PortfolioQryResponse response) {
        PortfolioSerBean serBean = new PortfolioSerBean();
        BeanMapUtils.map(response, serBean);
        return serBean;
    }
}
