package com.kuainiu.qt.data.util;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.biz.bean.*;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.request.*;
import com.kuainiu.qt.data.service.bean.*;
import com.kuainiu.qt.data.service.bean.trans.PortfolioQrySerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.trans.PortfolioSerBean;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BizBeanUtils {
    public static SnapshotPortfolioInBean buildSnapshotPortfolioInBean(InfoRatioRequest request) throws BizException {
        if (null == request) {
            throw new BizException(QtDataRspCode.ERR_PARAM_ERROR);
        }
        SnapshotPortfolioInBean inBean = new SnapshotPortfolioInBean();
        BeanMapUtils.map(request, inBean);
        return inBean;
    }

    public static SnapshotPortfolioInBean buildSnapshotPortfolioInBean(PortfolioYieldRequest request) throws BizException{
        if (null == request) {
            throw new BizException(QtDataRspCode.ERR_PARAM_ERROR);
        }
        SnapshotPortfolioInBean inBean = new SnapshotPortfolioInBean();
        BeanMapUtils.map(request, inBean);
        return inBean;
    }

    public static List<SnapshotPortfolioOutBean> buildSnapshotPortfolioOutBeanList(List<SnapshotPortfolioSerBean> serBeanList) throws BizException {
        List<SnapshotPortfolioOutBean>  outBeans;
        try {
            outBeans = BeanMapUtils.mapAsList(serBeanList, SnapshotPortfolioOutBean.class);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("[copy list fail] {}", JSON.toJSONString(serBeanList));
            throw new BizException(QtDataRspCode.ERR_SYS_ERROR);
        }
        return outBeans;
    }

    public static SnapshotPortfolioInBean buildSnapshotPortfolioInBean(PortfolioLastRecordPerDayRequest request) {
        SnapshotPortfolioInBean inBean = new SnapshotPortfolioInBean();
        BeanMapUtils.map(request, inBean);
        return inBean;
    }

    public static PortfolioInBean buildPortfolioInBean(PortfolioSerBean serBean) throws BizException {
        PortfolioInBean portfolioInBean = new PortfolioInBean();
        BeanMapUtils.map(serBean, portfolioInBean);
        return portfolioInBean;
    }

    public static List<StrategyInBean> buildStrategyList(List<StrategySerBean> strategySerBeanList) throws BizException {
        try {
            return BeanMapUtils.mapAsList(strategySerBeanList, StrategyInBean.class);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BizException(QtDataRspCode.ERR_COPY_LIST);
        }
    }

    public static SnapshotPortfolioOutBean buildSnapshotPortfolioOutBean(SnapshotPortfolioSerBean reqSerBean) {
        SnapshotPortfolioOutBean outBean = new SnapshotPortfolioOutBean();
        BeanMapUtils.map(reqSerBean, outBean);
        return outBean;
    }

    public static SnapshotPortfolioInBean buildSnapshotPortfolioInBean(SnapshotPortfolioRequest request) {
        SnapshotPortfolioInBean inBean = new SnapshotPortfolioInBean();
        BeanMapUtils.map(request, inBean);
        return inBean;
    }

    public static PortfolioReqSerBean buildPortfolioReqSerBean(PortfolioInBean inBean) {
        PortfolioReqSerBean reqSerBean = new PortfolioReqSerBean();
        BeanMapUtils.map(inBean, reqSerBean);
        return reqSerBean;
    }

    public static PortfolioOutBean buildPortfolioOutBean(PortfolioQrySerBean serBean) throws ServiceException {
        PortfolioOutBean outBean = new PortfolioOutBean();
        BeanMapUtils.map(serBean, outBean);
        List<StkPositionOutBean> stkPositionList = new ArrayList<>();
        List<FuturesPositionOutBean> futuresPositionList = new ArrayList<>();
        List<CashflowOutBean> cashflowList = new ArrayList<>();
        List<StkAccountOutBean> stkAccountList = new ArrayList<>();
        List<FuturesAccountOutBean> futuresAccountList = new ArrayList<>();
        try {
            outBean.setStkPositionList(stkPositionList);
            serBean.getStkPositionList().forEach(stkPositionSerBean -> {
                StkPositionOutBean stkPositionOutBean = new StkPositionOutBean();
                BeanMapUtils.map(stkPositionSerBean, stkPositionOutBean);
                StkAssetDetailFeeOutBean feeOutBean = new StkAssetDetailFeeOutBean();
                BeanMapUtils.map(stkPositionSerBean.getStkFee(), feeOutBean);
                stkPositionOutBean.setStkFee(feeOutBean);
                stkPositionList.add(stkPositionOutBean);
            });
            outBean.setStkPositionList(stkPositionList);
            futuresPositionList = BeanMapUtils.mapAsList(serBean.getFuturesPositionList(), FuturesPositionOutBean.class);
            outBean.setFuturesPositionList(futuresPositionList);
            cashflowList = BeanMapUtils.mapAsList(serBean.getCashflowList(), CashflowOutBean.class);
            outBean.setCashflowList(cashflowList);
            serBean.getStkAccountList().forEach(stkAccountSerBean -> {
                StkAccountOutBean stkAccountOutBean = new StkAccountOutBean();
                BeanMapUtils.map(stkAccountSerBean, stkAccountOutBean);
                StkFeeOutBean feeOutBean = new StkFeeOutBean();
                BeanMapUtils.map(stkAccountSerBean.getTransactionCost(), feeOutBean);
                stkAccountOutBean.setTransactionCost(feeOutBean);
                stkAccountList.add(stkAccountOutBean);
            });
            outBean.setStkAccountList(stkAccountList);
            futuresAccountList = BeanMapUtils.mapAsList(serBean.getFuturesAccountList(), FuturesAccountOutBean.class);
            outBean.setFuturesAccountList(futuresAccountList);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("[copy list fail] {}", JSON.toJSONString(serBean));
            throw new ServiceException(QtDataRspCode.ERR_SYS_ERROR);
        }
        return outBean;
    }

    public static StkPositionInBean buildStkPositionQryInBean(StkPositionPnlRequest request) {
        StkPositionInBean inBean = new StkPositionInBean();
        BeanMapUtils.map(request, inBean);
        return inBean;
    }

    public static StkPositionReqSerBean buildStkPositionReqSerBean(StkPositionInBean inBean) {
        StkPositionReqSerBean serBean = new StkPositionReqSerBean();
        BeanMapUtils.map(inBean, serBean);
        return serBean;
    }

    public static StkPositionOutBean buildStkPositionOutBean(StkPositionSerBean serBean) {
        StkPositionOutBean outBean = new StkPositionOutBean();
        BeanMapUtils.map(serBean, outBean);
        return outBean;
    }

    public static FuturesPositionReqSerBean buildFuturesPositionReqSerBean(FuturesPositionInBean inBean) {
        FuturesPositionReqSerBean reqSerBean = new FuturesPositionReqSerBean();
        BeanMapUtils.map(inBean, reqSerBean);
        return reqSerBean;
    }

    public static FuturesPositionOutBean buildFuturesPositionOutBean(FuturesPositionSerBean serBean) {
        FuturesPositionOutBean outBean = new FuturesPositionOutBean();
        BeanMapUtils.map(serBean, outBean);
        return outBean;
    }

    public static FuturesPositionInBean buildFuturesPositionQryInBean(FuturesPositionPnlRequest request) {
        FuturesPositionInBean inBean = new FuturesPositionInBean();
        BeanMapUtils.map(request, inBean);
        return inBean;
    }
}
