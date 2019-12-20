package com.sb.controller;

import com.sb.po.PeopleInfos;
import com.sb.service.PeopleInfosService;
import com.sb.mapper.PeopleInfosMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@CrossOrigin(origins="*", maxAge=3600) 
@RestController 
@RequestMapping("/api")
public class PagePluginController{
    @Autowired 
    private PeopleInfosService peopleInfosService;

    @Autowired 
    private PeopleInfosMapper peopleInfosMapper;

    static Logger logger = LoggerFactory.getLogger(PagePluginController.class);

    @RequestMapping(value="/plugin-data-test", method=RequestMethod.GET)
    public int getData(){
        return 250;
    }

    @RequestMapping(value="/get-infos-by-plugin", method=RequestMethod.POST) 
    // public PageInfo getInfosByPlugin(Map params){
    public Map getInfosByPlugin(@RequestBody Map params){
        Map infos = new HashMap();
        Integer pageNum = Integer.parseInt(params.get("pageNum").toString());
        logger.info("pageNum:{}", pageNum);
        Integer pageSize = Integer.parseInt(params.get("pageSize").toString());
        logger.info("pageSize:{}", pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<PeopleInfos> infosLi = peopleInfosMapper.selectAllPeopleInfos();
        logger.info("infos:{}", infosLi);
        PageInfo pageInfos = new PageInfo<PeopleInfos>(infosLi);
        logger.info("pageInfos:{}", pageInfos);
        infos.put("pageHelper", infosLi);
        infos.put("pageInfo", pageInfos);
        return infos;
        // return 250;
        // return infosLi;
        // PageInfo pageInfos = peopleInfosService.getInfosByPlugin(params);
        // return pageInfos;
    }
}