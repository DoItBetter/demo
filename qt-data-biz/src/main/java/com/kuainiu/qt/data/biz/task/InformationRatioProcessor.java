package com.kuainiu.qt.data.biz.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.kuainiu.qt.data.biz.SnapshotPortfolioBiz;
import com.kuainiu.qt.data.biz.bean.processor.PortfolioInformationRatioProcessorInBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

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
public class InformationRatioProcessor extends BaseProcessor {

    @Autowired
    SnapshotPortfolioBiz snapshotPortfolioBiz;

    @Override
    public ProcessResult process(JobContext jobContext) {
        try {
            log.info("[Biz][Portfolio] InformationRatioProcessor start... context= {}, JobParameters = {}" , jobContext.toString(), jobContext.getJobParameters());
            PortfolioInformationRatioProcessorInBean jobParam = JSON.parseObject(jobContext.getJobParameters(), PortfolioInformationRatioProcessorInBean.class);
            log.info("[Processor] jobParam={},isForce={}", jobParam, jobParam.isForce());
            snapshotPortfolioBiz.recordPortfolio(jobParam);
        } catch (Exception e) {
            log.error("[Biz][Portfolio] InformationRatioProcessor error , " , e);
        }
        return new ProcessResult(true);
    }
}
