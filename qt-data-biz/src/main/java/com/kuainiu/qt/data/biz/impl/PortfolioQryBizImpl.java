package com.kuainiu.qt.data.biz.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.biz.PortfolioQryBiz;
import com.kuainiu.qt.data.biz.bean.*;
import com.kuainiu.qt.data.biz.calc.FuturesAccountCalc;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.*;
import com.kuainiu.qt.data.service.bean.*;
import com.kuainiu.qt.data.util.BizBeanUtils;
import com.kuainiu.qt.data.util.BizReqSerBeanUtils;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.framework.common.util.CalculateUtils;
import com.kuainiu.qt.framework.common.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PortfolioQryBizImpl implements PortfolioQryBiz {
    @Autowired
    SnapshotPortfolioService snapshotPortfolioService;

    @Autowired
    SnapshotFuturesAccountService snapshotFuturesAccountService;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    FuturesAccountCalc futuresAccountCalc;

    @Autowired
    SnapshotStkAccountService snapshotStkAccountService;

    @Autowired
    SnapshotStkPositionService snapshotStkPositionService;

    @Autowired
    AidcQryService aidcQryService;

    @Autowired
    SnapshotFuturesPositionsService snapshotFuturesPositionsService;

    @Autowired
    SnapshotPortfolioCashflowService snapshotPortfolioCashflowService;

    @Override
    public PortfolioOutBean qryPortfolioStd(PortfolioInBean inBean) throws BizException {
        PortfolioOutBean outBean = new PortfolioOutBean();
        try {
            SnapshotPortfolioSerBean serBean = snapshotPortfolioService.getStdByPFCode(inBean.getPortfolioCode());
            outBean.setStd(serBean.getStd());
        } catch (ServiceException e) {
            throw new BizException(QtDataRspCode.ERR_QRY_SNAPSHOT_PORTFOLIO_STD, e.getMsg());
        }
        return outBean;
    }

    @Override
    public SnapshotPortfolioOutBean qryInfoRatio(SnapshotPortfolioInBean inBean) throws BizException {
        SnapshotPortfolioOutBean  outBean = new SnapshotPortfolioOutBean();
        try {
            SnapshotPortfolioSerBean reqSerBean = snapshotPortfolioService.getInfoRatioByPFCode(inBean.getPortfolioCode());
            outBean.setInformationRatio(reqSerBean.getInformationRatio());
        } catch (ServiceException e) {
            throw new BizException(QtDataRspCode.ERR_PORTFOLIOSNAPSHOT_INFO_QRY_FAIL, e.getMsg());
        }
        return outBean;
    }

    @Override
    public List<SnapshotPortfolioOutBean> qrySnapshotPortfolioList(SnapshotPortfolioInBean inBean) throws BizException {
        List<SnapshotPortfolioOutBean> snapshotPortfolioOutBeanList = new ArrayList<>();

        try {
            SnapshotPortfolioReqSerBean reqSerBean = BizReqSerBeanUtils.buildSnapshotPortfolioReqSerBean(inBean);
            List<SnapshotPortfolioSerBean> snapshotPortfolioSerBeanList = snapshotPortfolioService.findSnapshotPortfolioList(reqSerBean);
            if (null != snapshotPortfolioSerBeanList) {
                snapshotPortfolioOutBeanList = BizBeanUtils.buildSnapshotPortfolioOutBeanList(snapshotPortfolioSerBeanList);
            }
        } catch (ServiceException e) {
            throw new BizException(QtDataRspCode.ERR_PORTFOLIO_LIST_QRY_FAIL, e.getMsg());
        }
        return snapshotPortfolioOutBeanList;
    }

    @Override
    public List<SnapshotPortfolioOutBean> qryLastRecordPerDay(SnapshotPortfolioInBean inBean) throws BizException {
        List<SnapshotPortfolioOutBean> snapshotPortfolioOutBeanList;

        try {
            List<SnapshotPortfolioSerBean> snapshotPortfolioSerBeanList = snapshotPortfolioService.findLastRecordPerDayByPFCode(inBean.getPortfolioCode());
            snapshotPortfolioOutBeanList = BizBeanUtils.buildSnapshotPortfolioOutBeanList(snapshotPortfolioSerBeanList);
        } catch (ServiceException e) {
            throw new BizException(QtDataRspCode.ERR_PORTFOLIO_LIST_QRY_FAIL, e.getMsg());
        }
        return snapshotPortfolioOutBeanList;
    }

    @Override
    public PortfolioOutBean qryPortfolioFromLocal(PortfolioInBean inBean) throws BizException {
        PortfolioOutBean outBean;
        try {
            PortfolioReqSerBean reqSerBean = BizBeanUtils.buildPortfolioReqSerBean(inBean);
            PortfolioQrySerBean serBean = portfolioService.qryPortfolio(reqSerBean);
            outBean = BizBeanUtils.buildPortfolioOutBean(serBean);
        } catch (ServiceException e) {
            throw new BizException(QtDataRspCode.ERR_PORTFOLIOSNAPSHOT_INFO_QRY_FAIL, e.getMsg());
        }
        return outBean;
    }

    @Override
    public PortfolioOutBean qryPortfolio(PortfolioInBean inBean) throws BizException {
        log.info("[Biz][Portfolio] qry,inBean={}", JSON.toJSONString(inBean));
        if (null == inBean) {
            throw new BizException(QtDataRspCode.ERR_EMPTY_IN_BEAN);
        }
        PortfolioOutBean outBean;
        try {
            //查询
            outBean = qryPortfolioFromDB(inBean);
        } catch (ServiceException e) {
            log.info("[Biz][Portfolio] qry fail,e={}", e);
            throw new BizException(QtDataRspCode.ERR_PORTFOLIO_QRY_FAIL, e.getMsg());
        }
        log.info("[Biz][Portfolio] qry,outBean={}", JSON.toJSONString(outBean));
        return outBean;
    }

    private PortfolioOutBean qryPortfolioFromDB(PortfolioInBean inBean) throws ServiceException, BizException {
        PortfolioOutBean outBean = new PortfolioOutBean();
        try {
            //投资组合基本信息
            SnapshotPortfolioSerBean portfolioSerBean = snapshotPortfolioService.findOneOneMinuteAgo(inBean.getPortfolioCode());
            BeanMapUtils.map(portfolioSerBean, outBean);

            //计算总权益 begin
            String snapShotCode = portfolioSerBean.getSnapshotCode();
            List<SnapshotFuturesAccountSerBean> snapshotFuturesAccountSerBeanList = snapshotFuturesAccountService.getListBySnapshotCode(snapShotCode);
            BigDecimal totalFund = BigDecimal.ZERO;
            for(SnapshotFuturesAccountSerBean snapshotFuturesAccountSerBean:snapshotFuturesAccountSerBeanList){
                BigDecimal tmpTotalFund = futuresAccountCalc.calcTotalFund(snapshotFuturesAccountSerBean);
                totalFund = CalculateUtils.sumBigDecimal(totalFund, tmpTotalFund);
            }
            //股票部分
            List<SnapshotStkAccountSerBean> stkAccountList = snapshotStkAccountService.getListBySnapshotCode(snapShotCode);
            if (stkAccountList.size() != CommonConstant.ZERO) {
                for (SnapshotStkAccountSerBean stkAccount : stkAccountList) {
                    totalFund = CalculateUtils.sumBigDecimal(stkAccount.getTotalValue(), totalFund);
                }
            }
            List<StkAccountOutBean> stkAccountOutBeanList = BeanMapUtils.mapAsList(stkAccountList, StkAccountOutBean.class);
            outBean.setStkAccountList(stkAccountOutBeanList);
            outBean.setTotalFund(totalFund);
            //计算总权益 end
            //股票仓位
            List<SnapshotStkPositionSerBean> snapshotStkPositionSerBeanList = snapshotStkPositionService.getListBySnapshotCode(portfolioSerBean.getSnapshotCode());
            List<StkPositionOutBean> stkPositionOutBeanList = new ArrayList<>();
            for (SnapshotStkPositionSerBean snapshotStkPositionSerBean : snapshotStkPositionSerBeanList) {
                StkPositionOutBean stkPositionOutBean = new StkPositionOutBean();
                BeanMapUtils.map(snapshotStkPositionSerBean, stkPositionOutBean);
                stkPositionOutBean.setAssetName(aidcQryService.getAssetName(stkPositionOutBean.getTransBoard(), stkPositionOutBean.getAssetNo()));
                stkPositionOutBeanList.add(stkPositionOutBean);
            }
            outBean.setStkPositionList(stkPositionOutBeanList);

            //期货仓位
            List<SnapshotFuturesPositionsSerBean> snapshotFuturesPositionsSerBeanList = snapshotFuturesPositionsService.getListBySnapshotCode(portfolioSerBean.getSnapshotCode());
            List<FuturesPositionOutBean> futuresPositionOutBeanList = BeanMapUtils.mapAsList(snapshotFuturesPositionsSerBeanList, FuturesPositionOutBean.class);
            outBean.setFuturesPositionList(futuresPositionOutBeanList);

            //出入金
            List<SnapshotPortfolioCashflowSerBean> snapshotPortfolioCashflowSerBeanList = snapshotPortfolioCashflowService.getListBySnapshotCode(portfolioSerBean.getSnapshotCode());
            List<CashflowOutBean> cashflowOutBeanList = BeanMapUtils.mapAsList(snapshotPortfolioCashflowSerBeanList, CashflowOutBean.class);
            outBean.setCashflowList(cashflowOutBeanList);

        } catch (IllegalAccessException | InstantiationException e) {
            throw new BizException(QtDataRspCode.SYS_ERROR, "copy list fail");
        }
        return outBean;
    }
}
