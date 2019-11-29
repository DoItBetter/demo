package com.kuainiu.qt.data.biz.impl;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.biz.PortfolioBiz;
import com.kuainiu.qt.data.biz.PortfolioQryBiz;
import com.kuainiu.qt.data.biz.bean.*;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioProcessorInBean;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.common.util.seq.SysSeqNoManager;
import com.kuainiu.qt.data.common.util.seq.SysSeqRuleEnum;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.code.StkFeeTypeCode;
import com.kuainiu.qt.data.service.PortfolioService;
import com.kuainiu.qt.data.service.SnapshotPortfolioService;
import com.kuainiu.qt.data.service.bean.*;
import com.kuainiu.qt.data.util.BizBeanUtils;
import com.kuainiu.qt.data.util.CalculateUtil;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import com.kuainiu.qt.trans.facade.code.PortfolioStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PortfolioBizImpl implements PortfolioBiz {
    @Autowired
    SnapshotPortfolioService snapshotPortfolioService;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    PortfolioQryBiz portfolioQryBiz;

    private Date belongTime;

    @Autowired
    SysSeqNoManager sysSeqNoManager;

    @Override
    public void recordSnapshot(PortfolioProcessorInBean portfolioProcessorInBean) throws BizException {
        // 校验是否是交易日  + 判断是否开市时间段 入参portfolioProcessorInBean里面  isForce 是否是true
        try {
            if (!snapshotPortfolioService.needRun() && !portfolioProcessorInBean.isForce()) {
                log.warn("today is not trans day or curr time is not in open market!");
                return;
            }
            //所属时间
            belongTime = QtDateUtils.getZeroSecondTime(QtDateUtils.getCurrDate());

            PortfolioReqSerBean portfolioReqSerBean = new PortfolioReqSerBean();
            portfolioReqSerBean.setStatus(PortfolioStatusCode.VALID.getCode());
            List<PortfolioSerBean> portfolioList = portfolioService.findAll(portfolioReqSerBean);

            for (PortfolioSerBean serBean : portfolioList) {
                PortfolioInBean inBean = BizBeanUtils.buildPortfolioInBean(serBean);
                PortfolioOutBean portfolio = portfolioQryBiz.qryPortfolioFromLocal(inBean);
                log.info("qryPortfolioFromLocal serBean" + serBean);
                SnapshotGroupSerBean groupSerBean = buildGroupBean(portfolio);
                snapshotPortfolioService.setSnapshotPortfolioTx(groupSerBean);
            }
        } catch (ServiceException e) {
            log.error("[recordPortfolio fail] msg:[{}]", e.getMsg());
            throw new BizException(QtDataRspCode.ERR_PORTFOLIO_SS_RECORD);
        }
    }
    private SnapshotGroupSerBean buildGroupBean(PortfolioOutBean portfolio) throws ServiceException {
        SnapshotGroupSerBean ssGroupBean = new SnapshotGroupSerBean();
        String portfolioCode = portfolio.getPortfolioCode();
        try {
            //投资组合的信息
            SnapshotPortfolioSerBean ssPortfolio = new SnapshotPortfolioSerBean();
            BeanMapUtils.map(portfolio, ssPortfolio);
            Date belongDate = getBelongDate(portfolioCode);
            ssPortfolio.setBelongTime(belongTime);
            ssPortfolio.setBelongDate(belongDate);
            String snapshotCode = sysSeqNoManager.getSeqNo(SysSeqRuleEnum.UNIQUE_CODE_SEQ);
            ssPortfolio.setSnapshotCode(snapshotCode);
            ssGroupBean.setPortfolioSerBean(ssPortfolio);

            //出入金信息
            List<SnapshotPortfolioCashflowSerBean> ssCashflowList = buildCashflowList(portfolio.getCashflowList(), snapshotCode);
            ssGroupBean.setPortfolioCashflowSerBeanList(ssCashflowList);

            //股票仓位
            List<SnapshotStkPositionSerBean> stkPositionList = buildStkPositionList(portfolio.getStkPositionList(), snapshotCode);
            ssGroupBean.setStkPositionSerBeanList(stkPositionList);

            //期货仓位
            List<FuturesPositionSerBean> futuresPositionList = buildFuturesPositionList(portfolio.getFuturesPositionList(), snapshotCode);
            ssGroupBean.setFuturesPositionSerBeanList(futuresPositionList);

            //期货账户信息
            List<SnapshotFuturesAccountSerBean> futuresAccountList = buildFuturesAccountList(portfolio.getFuturesAccountList(), snapshotCode);
            ssGroupBean.setFuturesAccountList(futuresAccountList);

            //股票账户及费用
            List<SnapshotStkAccountSerBean> stkAccountList = buildStkAccountList(portfolio.getStkAccountList(), snapshotCode);
            ssGroupBean.setStkAccountList(stkAccountList);

        } catch (Exception e) {
            log.error("[build SnapshotGroupSerBean fail]", e);
        }
        log.info("[Processor][Portfolio]serBean={}", JSON.toJSONString(ssGroupBean));
        return ssGroupBean;
    }

    private Date getBelongDate(String portfolioCode) throws ServiceException {
        Date belongDate = null;
        if (QtDateUtils.isBeforeOpenMarket()) {
            //查最后一条的时间
            SnapshotPortfolioSerBean snapshotPortfolioSerBean = snapshotPortfolioService.getBeforeBelongTime(portfolioCode, belongTime);
            if (snapshotPortfolioSerBean.getBelongDate() == null) {
                log.warn("[Processor]last ss portfolio not exit，portfolioCode={}", portfolioCode);
                return QtDateUtils.getCurrDate();
            }
            belongDate = snapshotPortfolioSerBean.getBelongDate();
            log.info("[Processor]投资组合的所属日期，portfolioCode={},belongDate={}", portfolioCode, belongDate);
            return snapshotPortfolioSerBean.getBelongDate();
        }
        belongDate = QtDateUtils.getCurrDate();
        log.info("[Processor]投资组合的所属日期，prtfolioCode={},belongDate={}", portfolioCode, belongDate);

        return belongDate;
    }

    private List<SnapshotPortfolioCashflowSerBean> buildCashflowList(List<CashflowOutBean> cashflowList, String snapshotCode) {
        List<SnapshotPortfolioCashflowSerBean> ssCashflowList = new ArrayList<>();
        if (cashflowList == null) {
            return ssCashflowList;
        }
        for (CashflowOutBean cashflow : cashflowList) {
            SnapshotPortfolioCashflowSerBean ssCashflow = new SnapshotPortfolioCashflowSerBean();
            BeanMapUtils.map(cashflow, ssCashflow);
            ssCashflow.setSnapshotCode(snapshotCode);
            ssCashflowList.add(ssCashflow);
        }
        return ssCashflowList;
    }

    private List<SnapshotStkPositionSerBean> buildStkPositionList(List<StkPositionOutBean> positionList, String snapshotCode) {
        List<SnapshotStkPositionSerBean> ssPositionList = new ArrayList<>();
        if (positionList == null) {
            return ssPositionList;
        }
        for (StkPositionOutBean position : positionList) {
            SnapshotStkPositionSerBean ssPosition = new SnapshotStkPositionSerBean();
            BeanMapUtils.map(position, ssPosition);
            ssPosition.setSnapshotCode(snapshotCode);
            ssPosition.setBelongTime(belongTime);
            ssPositionList.add(ssPosition);
        }
        return ssPositionList;
    }

    private List<FuturesPositionSerBean> buildFuturesPositionList(List<FuturesPositionOutBean> positionList, String snapshotCode) {
        List<FuturesPositionSerBean> ssPositionList = new ArrayList<>();
        if (positionList == null) {
            return ssPositionList;
        }
        for (FuturesPositionOutBean position : positionList) {
            FuturesPositionSerBean ssPosition = new FuturesPositionSerBean();
            BeanMapUtils.map(position, ssPosition);
            ssPosition.setSnapshotCode(snapshotCode);
            ssPosition.setBelongTime(belongTime);
            ssPositionList.add(ssPosition);
        }
        return ssPositionList;
    }

    private List<SnapshotFuturesAccountSerBean> buildFuturesAccountList(List<FuturesAccountOutBean> accountList, String snapshotCode) {
        List<SnapshotFuturesAccountSerBean> ssAccountList = new ArrayList<>();
        if (accountList == null) {
            return ssAccountList;
        }
        for (FuturesAccountOutBean account : accountList) {
            SnapshotFuturesAccountSerBean ssAccount = new SnapshotFuturesAccountSerBean();
            BeanMapUtils.map(account, ssAccount);
            ssAccount.setSnapshotCode(snapshotCode);
            ssAccount.setBelongTime(belongTime);
            ssAccountList.add(ssAccount);
        }
        return ssAccountList;
    }

    private List<SnapshotStkAccountSerBean> buildStkAccountList(List<StkAccountOutBean> accountList, String snapshotCode) {
        List<SnapshotStkAccountSerBean> ssAccountList = new ArrayList<>();
        if (accountList == null) {
            return ssAccountList;
        }
        for (StkAccountOutBean account : accountList) {
            SnapshotStkAccountSerBean ssAccount = new SnapshotStkAccountSerBean();
            ssAccount.setSnapshotCode(snapshotCode);
            ssAccount.setBelongTime(belongTime);
            List<SnapshotStkFeeSerBean> stkFeeList = new ArrayList<>();
            BigDecimal yj = account.getTransactionCost().getFeeYj();
            BigDecimal yhs = account.getTransactionCost().getFeeYhs();
            BigDecimal trade = account.getTransactionCost().getFeeTrade();
            BigDecimal transfer = account.getTransactionCost().getFeeTransfer();

            if (!CalculateUtil.isZero(yj)) {
                SnapshotStkFeeSerBean snapshotStkFee = new SnapshotStkFeeSerBean();
                snapshotStkFee.setFeeType(StkFeeTypeCode.FEE_TYPE_YJ.getCode());
                snapshotStkFee.setFeeInfo(yj.toString());
                stkFeeList.add(snapshotStkFee);
            }
            if (!CalculateUtil.isZero(yhs)) {
                SnapshotStkFeeSerBean snapshotStkFee = new SnapshotStkFeeSerBean();
                snapshotStkFee.setFeeType(StkFeeTypeCode.FEE_TYPE_YHS.getCode());
                snapshotStkFee.setFeeInfo(yhs.toString());
                stkFeeList.add(snapshotStkFee);
            }
            if (!CalculateUtil.isZero(trade)) {
                SnapshotStkFeeSerBean snapshotStkFee = new SnapshotStkFeeSerBean();
                snapshotStkFee.setFeeType(StkFeeTypeCode.FEE_TYPE_TRADE.getCode());
                snapshotStkFee.setFeeInfo(trade.toString());
                stkFeeList.add(snapshotStkFee);
            }
            if (!CalculateUtil.isZero(transfer)) {
                SnapshotStkFeeSerBean snapshotStkFee = new SnapshotStkFeeSerBean();
                snapshotStkFee.setFeeType(StkFeeTypeCode.FEE_TYPE_TRANSFER.getCode());
                snapshotStkFee.setFeeInfo(transfer.toString());
                stkFeeList.add(snapshotStkFee);
            }
            ssAccount.setStkFeeList(stkFeeList);
            BeanMapUtils.map(account, ssAccount);
            ssAccountList.add(ssAccount);
        }
        return ssAccountList;
    }
}
