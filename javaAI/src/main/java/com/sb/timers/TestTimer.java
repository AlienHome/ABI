package com.sb.timers;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class TestTimer extends QuartzJobBean{
    static Logger logger = LoggerFactory.getLogger(TestTimer.class);
    @Override 
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        logger.info("test timer 测试定时任务, 任务时间:{}", new Date());
    }
}