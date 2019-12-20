package com.sb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @MapperScan({"com.sb.dao","com.sb.mapper"})

@SpringBootApplication 
@MapperScan("com.sb.mapper")
// @MapperScan({"com.sb.dao","com.sb.mapper"})
@EnableCaching 
public class SbUsageApplication extends SpringBootServletInitializer{
    @Override 
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(SbUsageApplication.class);
    }
    private static Logger logger = LoggerFactory.getLogger(SbUsageApplication.class);

    public static void main(String[] args){
        SpringApplication.run(SbUsageApplication.class, args);
        logger.info("定时任务分布式节点 - quartz-cluster-node-second 已启动");
    }
}