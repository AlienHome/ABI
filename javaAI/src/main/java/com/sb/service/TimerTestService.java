package com.sb.service;

import com.sb.timers.TestTimer;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID; 
import java.util.Map;
import java.util.HashMap;

@Service 
@Transactional(rollbackFor = Exception.class)
public class TimerTestService{
    @Autowired 
    private Scheduler scheduler;

    public String startTask(Map taskInfos) throws Exception{
        buildTestTimer(taskInfos);
        return "create cron job every 5 seconds execute";
    }

    public void buildTestTimer(Map taskInfos) throws Exception{
        // String name = UUID.randomUUID().toString();
        String name = taskInfos.get("name").toString();
        String group = taskInfos.get("group").toString();
        String timeCycle = taskInfos.get("timeCycle").toString();
        // String weekCycle = taskInfos.get("weekCycle").toString();
        // String taskTime = "0 0"+timeCycle+"? *"+ weekCycle;
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(timeCycle);
        JobDetail jobDetail = JobBuilder.newJob(TestTimer.class)
                                        .withIdentity(name, group)
                                        .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity(name, group)
                                        .startNow()
                                        .withSchedule(scheduleBuilder)
                                        .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public String pauseTask(Map taskInfos) throws Exception {
        JobKey jobKey = new JobKey(taskInfos.get("name").toString(), taskInfos.get("group").toString());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if(jobDetail == null){
            return "task do not exists, check again!";
        }
        scheduler.pauseJob(jobKey);
        return "pause"+taskInfos.get("name").toString()+"task!";
    }
    
    public String deleteTask(Map taskInfos) throws Exception{
        JobKey jobKey = new JobKey(taskInfos.get("name").toString(), taskInfos.get("group").toString());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if(jobDetail == null){
            return "task do not exists, check again!";
        }
        scheduler.deleteJob(jobKey);
        return "Delete"+taskInfos.get("name").toString()+"task!";

    }
}