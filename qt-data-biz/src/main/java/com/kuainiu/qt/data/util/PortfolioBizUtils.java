package com.kuainiu.qt.data.util;

import com.kuainiu.qt.data.biz.bean.PortfolioInBean;
import com.kuainiu.qt.data.biz.bean.PortfolioOutBean;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.facade.bean.CashflowFacadeBean;
import com.kuainiu.qt.data.facade.bean.FuturesPositionFacadeBean;
import com.kuainiu.qt.data.facade.bean.PortfolioInfoFacadeBean;
import com.kuainiu.qt.data.facade.bean.StkPositionFacadeBean;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.request.PortfolioQryRequest;
import com.kuainiu.qt.data.facade.request.StdRequest;
import com.kuainiu.qt.data.facade.response.PortfolioQryResponse;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;

import java.util.List;

public class PortfolioBizUtils {

    public static PortfolioInBean buildPortfolioInBean(StdRequest request) throws BizException {
        if (null == request) {
            throw new BizException(QtDataRspCode.ERR_PARAM_ERROR);
        }
        PortfolioInBean inBean = new PortfolioInBean();
        BeanMapUtils.map(request, inBean);
        return inBean;
    }

    public static PortfolioInBean buildPortfolioInBean(PortfolioQryRequest request) {
        PortfolioInBean inBean = new PortfolioInBean();
        BeanMapUtils.map(request, inBean);
        return inBean;
    }

    public static PortfolioQryResponse buildPortfolioQryResponse(PortfolioOutBean outBean) throws BizException {
        PortfolioQryResponse response = new PortfolioQryResponse();
        PortfolioInfoFacadeBean facadeBean = new PortfolioInfoFacadeBean();
        BeanMapUtils.map(outBean, facadeBean);
        try {
            List<StkPositionFacadeBean> stkPositionList = BeanMapUtils.mapAsList(outBean.getStkPositionList(), StkPositionFacadeBean.class);
            List<FuturesPositionFacadeBean> futuresPositionList = BeanMapUtils.mapAsList(outBean.getFuturesPositionList(), FuturesPositionFacadeBean.class);
            List<CashflowFacadeBean> cashflowList = BeanMapUtils.mapAsList(outBean.getCashflowList(), CashflowFacadeBean.class);
            facadeBean.setStkPositionList(stkPositionList);
            facadeBean.setFuturesPositionList(futuresPositionList);
            facadeBean.setCashflowList(cashflowList);
            response.setData(facadeBean);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BizException(QtDataRspCode.ERR_COPY_LIST);
        }
        return response;
    }
}
