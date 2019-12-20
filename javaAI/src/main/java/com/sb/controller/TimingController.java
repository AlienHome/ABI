package com.sb.controller;

import com.sb.service.TimerTestService;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.scheduling.commonj.TimerManagerTaskScheduler;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.quartz.*;

@RestController 
@RequestMapping("/api/timing")
public class TimingController{
    @Autowired 
    private TimerTestService timerTestService;

    @RequestMapping(value="/start", method=RequestMethod.POST)
    public Map startTask(@RequestBody Map taskInfos) throws Exception {
        Map returnInfos = new HashMap();
        returnInfos.put("code", 200);
        returnInfos.put("infos", timerTestService.startTask(taskInfos));
        return returnInfos;
    }

    @RequestMapping(value="/pause", method=RequestMethod.POST) 
    public Map pauseTask(@RequestBody Map taskInfos) throws Exception{
        Map returnInfos = new HashMap();
        returnInfos.put("code", 200);
        returnInfos.put("infos", timerTestService.pauseTask(taskInfos));
        return returnInfos;
    }

    @RequestMapping(value="/delete", method=RequestMethod.POST) 
    public Map delelteTask(@RequestBody Map taskInfos) throws Exception{
        Map returnInfos = new HashMap();
        returnInfos.put("code", 200);
        returnInfos.put("infos", timerTestService.deleteTask(taskInfos));
        return returnInfos;
    }
}
