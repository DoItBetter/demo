package com.kuainiu.qt.data.util;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.biz.bean.*;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.request.PortfolioLastRecordPerDayRequest;
import com.kuainiu.qt.data.facade.request.InfoRatioRequest;
import com.kuainiu.qt.data.facade.request.PortfolioYieldRequest;
import com.kuainiu.qt.data.facade.request.SnapshotPortfolioRequest;
import com.kuainiu.qt.data.service.bean.PortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.PortfolioSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;
import com.kuainiu.qt.data.service.bean.StrategySerBean;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;

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
        List<StrategyInBean> strategyInBeanList = buildStrategyList(serBean.getStrategyList());
        portfolioInBean.setStrategyList(strategyInBeanList);
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

    public static PortfolioOutBean buildPortfolioOutBean(PortfolioSerBean serBean) {
        PortfolioOutBean outBean = new PortfolioOutBean();
        BeanMapUtils.map(serBean, outBean);
        return outBean;
    }
}
