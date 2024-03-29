package com.cx.qt.data.biz.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.cx.qt.data.exception.BizException;
import com.cx.qt.data.biz.PortfolioBiz;
import com.cx.qt.data.biz.bean.processor.PortfolioProcessorInBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/22
 * Time: 7:01 PM
 */
@Component
@Configurable
@EnableScheduling
@Slf4j
public class PortfolioProcessor extends BaseProcessor {
    @Autowired
    PortfolioBiz portfolioBiz;

    @Override
    public ProcessResult process(JobContext context) {
        log.info("[Processor] PortfolioProcessor start ..., context= {}, JobParameters = {}" , context.toString(), context.getJobParameters());
        try {
            PortfolioProcessorInBean jobParam = JSON.parseObject(context.getJobParameters(), PortfolioProcessorInBean.class);
            log.info("[Processor] jobParam={},isForce={}", jobParam, jobParam.isForce());
            portfolioBiz.recordSnapshot(jobParam);
        } catch (BizException e) {
            log.info("[Processor] record portfolio fail, e= {}", e.getMsg());
            return new ProcessResult(true, e.getMsg());
        } catch (Exception e) {
            log.info("[Processor] record portfolio fail, e= {}", e);
            return new ProcessResult(true);
        }
        return new ProcessResult(true);
    }
}
