package com.kuainiu.qt.data.biz.impl;

import com.kuainiu.qt.data.biz.SnapshotPortfolioBiz;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioInBean;
import com.kuainiu.qt.data.biz.bean.SnapshotPortfolioOutBean;
import com.kuainiu.qt.data.exception.BizException;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.facade.code.QtDataRspCode;
import com.kuainiu.qt.data.service.SnapshotPortfolioService;
import com.kuainiu.qt.data.service.bean.SnapshotPortfolioSerBean;
import com.kuainiu.qt.data.util.BizBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotPortfolioBizImpl implements SnapshotPortfolioBiz {
    @Autowired
    SnapshotPortfolioService snapshotPortfolioService;

    @Override
    public SnapshotPortfolioOutBean qryLastBeforeOpenMarket(SnapshotPortfolioInBean inBean) throws BizException {
        SnapshotPortfolioOutBean  outBean;
        try {
            SnapshotPortfolioSerBean serBean = snapshotPortfolioService.findLastBeforeOpenMarket(inBean.getPortfolioCode());
            outBean = BizBeanUtils.buildSnapshotPortfolioOutBean(serBean);
        } catch (ServiceException e) {
            throw new BizException(QtDataRspCode.ERR_PORTFOLIOSNAPSHOT_INFO_QRY_FAIL, e.getMsg());
        }
        return outBean;
    }
}
