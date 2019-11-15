package com.kuainiu.qt.data.util;

import com.kuainiu.qt.data.biz.bean.PortfolioInBean;
import com.kuainiu.qt.data.biz.bean.PortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.StdInBean;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.request.PortfolioQryRequest;
import com.kuainiu.qt.data.facade.request.StdRequest;
import com.kuainiu.qt.data.facade.response.PortfolioQryResponse;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;

public class PortfolioBizUtils {
    public static StdInBean buildPStdInBean(StdRequest request) {
        StdInBean inBean = new StdInBean();
        BeanMapUtils.map(request, inBean);
        return inBean;
    }

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

    public static PortfolioQryResponse buildPortfolioQryResponse(PortfolioOutBean outBean) {
        PortfolioQryResponse response = new PortfolioQryResponse();
        BeanMapUtils.map(outBean, response);
        return response;
    }
}
