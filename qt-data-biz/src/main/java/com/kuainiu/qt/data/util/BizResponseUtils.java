package com.kuainiu.qt.data.util;

import com.alibaba.fastjson.JSON;
import com.kuainiu.qt.data.biz.bean.PortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.facade.bean.SnapshotPortfolioFacadeBean;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.facade.response.SnapshotPortfolioListResponse;
import com.kuainiu.qt.data.facade.response.SnapshotPortfolioResponse;
import com.kuainiu.qt.data.facade.response.StkPositionPnlResponse;
import com.kuainiu.qt.framework.common.util.BeanMapUtils;
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

    public static StkPositionPnlResponse buildStkPositionQryResponse(PortfolioOutBean outBean) {
        StkPositionPnlResponse response = new StkPositionPnlResponse();
        BeanMapUtils.map(outBean, response);
        return response;
    }
}
