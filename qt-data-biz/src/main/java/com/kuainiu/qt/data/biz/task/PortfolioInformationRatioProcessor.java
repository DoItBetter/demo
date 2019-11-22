package com.kuainiu.qt.data.biz.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioInformationRatioProcessorInBean;
import com.kuainiu.qt.data.common.util.QtDateUtils;
import com.kuainiu.qt.data.exception.ServiceException;
import com.kuainiu.qt.data.service.PortfolioService;
import com.kuainiu.qt.data.service.SnapshotPortfolioService;
import com.kuainiu.qt.data.service.bean.PortfolioSerBean;
import com.kuainiu.qt.data.service.http.impl.AidcCDHttpImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jixuan
 * Date: 2019-08-21
 * Time: 19:14
 */
@Component
@Configurable
@EnableScheduling
@Slf4j
public class PortfolioInformationRatioProcessor extends BaseProcessor {

    @Autowired
    AidcCDHttpImpl portfolioHttp;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    SnapshotPortfolioService snapshotPortfolioService;

    @Override
    public ProcessResult process(JobContext jobContext) {
        try {
            PortfolioInformationRatioProcessorInBean jobParam = JSON.parseObject(jobContext.getJobParameters(), PortfolioInformationRatioProcessorInBean.class);

            log.info("[Processor] jobParam={},isForce={}", jobParam, jobParam.isForce());
            recordPortfolio(jobParam);
        } catch (Exception e) {
            log.error("[Biz][Portfolio] InformationRatioProcessor error , " + e);
        }
        return new ProcessResult(true);
    }

    public void recordPortfolio(PortfolioInformationRatioProcessorInBean jobParam) {
        Date belongTime = QtDateUtils.getMinuteTimestamp();
        log.info("[Biz][Portfolio] InformationRatioProcessor start,belongTime={}", belongTime);
        try {
            List<PortfolioSerBean> portfolioSerBeanList = portfolioService.findDistinctPortfolioCode();
            log.info("[Biz][Portfolio]snapshot={}", portfolioSerBeanList);
            for (PortfolioSerBean portfolioSerBean : portfolioSerBeanList) {
                String portfolioCode = portfolioSerBean.getPortfolioCode();
                if (snapshotPortfolioService.needRun() || jobParam.isForce()) {
                    snapshotPortfolioService.getPortfolioByBelongTime(portfolioCode, QtDateUtils.getNowZeroSecond());
                    snapshotPortfolioService.calcPortfolio(portfolioCode, belongTime);
                }
            }
        } catch (ServiceException e) {
            log.info("[Biz][Portfolio] InformationRatioProcessor fail", e);
        }
        log.info("[Biz][Portfolio] InformationRatioProcessor end");
    }
}
