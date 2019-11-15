package com.kuainiu.qt.data.biz.impl;

import com.kuainiu.qt.data.biz.PortfolioQryBiz;
import com.kuainiu.qt.data.biz.bean.PortfolioInBean;
import com.kuainiu.qt.data.biz.bean.PortfolioOutBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.PortfolioService;
import com.kuainiu.qt.data.service.SnapshotPortfolioService;
import com.kuainiu.qt.data.service.bean.PortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.PortfolioSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioReqSerBean;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;
import com.kuainiu.qt.data.util.BizBeanUtils;
import com.kuainiu.qt.data.util.BizReqSerBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PortfolioQryBizImpl implements PortfolioQryBiz {
    @Autowired
    SnapshotPortfolioService snapshotPortfolioService;

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

    @Autowired
    PortfolioService portfolioService;

    @Override
    public PortfolioOutBean qryPortfolioFromLocal(PortfolioInBean inBean) throws BizException {
        PortfolioOutBean outBean;
        try {
            PortfolioReqSerBean reqSerBean = BizBeanUtils.buildPortfolioReqSerBean(inBean);
            PortfolioSerBean serBean = portfolioService.qryPortfolio(reqSerBean);
            outBean = BizBeanUtils.buildPortfolioOutBean(serBean);
        } catch (ServiceException e) {
            throw new BizException(QtDataRspCode.ERR_PORTFOLIOSNAPSHOT_INFO_QRY_FAIL, e.getMsg());
        }
        return outBean;
    }
}
