package com.cx.qt.data.util;

import com.alibaba.fastjson.JSON;
import com.cx.qt.data.exception.BizException;
import com.cx.qt.data.biz.bean.FuturesPositionOutBean;
import com.cx.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.cx.qt.data.biz.bean.StkPositionOutBean;
import com.cx.qt.data.facade.bean.FuturesPositionFacadeBean;
import com.cx.qt.data.facade.bean.SnapshotPortfolioFacadeBean;
import com.cx.qt.data.facade.bean.StkAssetDetailFeeFacadeBean;
import com.cx.qt.data.facade.bean.StkPositionFacadeBean;
import com.cx.qt.data.facade.code.QtDataRspCode;
import com.cx.qt.data.facade.response.FuturesPositionResponse;
import com.cx.qt.data.facade.response.SnapshotPortfolioListResponse;
import com.cx.qt.data.facade.response.SnapshotPortfolioResponse;
import com.cx.qt.data.facade.response.StkPositionResponse;
import com.cx.qt.framework.common.util.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BizResponseUtils {
    public static void buildQtTransSnapshotPortfolioQryResponse(List<SnapshotPortfolioOutBean> outBeanList, SnapshotPortfolioListResponse response) throws BizException {
        try {
            List<SnapshotPortfolioFacadeBean> stkTransFacadeBeanList = BeanMapUtils.mapAsList(outBeanList, SnapshotPortfolioFacadeBean.class);
            response.setData(stkTransFacadeBeanList);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("[copy list fail] {}", JSON.toJSONString(outBeanList));
            throw new BizException(QtDataRspCode.SYS_ERROR);
        }
    }

    public static SnapshotPortfolioResponse buildSnapshotPortfolioResponse(SnapshotPortfolioOutBean outBean) {
        SnapshotPortfolioResponse response = new SnapshotPortfolioResponse();
        BeanMapUtils.map(outBean, response);
        return response;
    }

    public static StkPositionResponse buildStkPositionQryResponse(StkPositionOutBean outBean) {
        StkPositionResponse response = new StkPositionResponse();
        StkPositionFacadeBean facadeBean = new StkPositionFacadeBean();
        BeanMapUtils.map(outBean, facadeBean);
        if (outBean.getStkFee() == null) {
            response.setData(facadeBean);
            return response;
        }
        StkAssetDetailFeeFacadeBean fee = new StkAssetDetailFeeFacadeBean();
        BeanMapUtils.map(outBean.getStkFee(), fee);
        facadeBean.setStkFee(fee);
        response.setData(facadeBean);
        return response;
    }

    public static FuturesPositionResponse buildFuturesPositionQryResponse(FuturesPositionOutBean outBean) {
        FuturesPositionResponse response = new FuturesPositionResponse();
        FuturesPositionFacadeBean facadeBean = new FuturesPositionFacadeBean();
        BeanMapUtils.map(outBean, facadeBean);
        response.setData(facadeBean);
        return response;
    }
}
