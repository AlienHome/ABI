package com.sb.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sb.service.PeopleInfosService;
// import com.sb.dao.PeopleInfosDao;
import com.sb.mapper.PeopleInfosMapper;
import com.sb.po.PeopleInfos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CacheConfig(cacheNames="peopleInfos")
@Service 
@Transactional
public class PeopleInfosServiceImpl implements PeopleInfosService{

    @Autowired 
    private PeopleInfosMapper peopleInfosMapper;

    static Logger logger = LoggerFactory.getLogger(PeopleInfosServiceImpl.class);

    @Override 
    public List<PeopleInfos> getAllPeopleInfos(){
        // List<PeopleInfos> infos = peopleInfosDao.selectAllPeopleInfos();
        List<PeopleInfos> infos = peopleInfosMapper.selectAllPeopleInfos();
        logger.info("test");
        return infos;
    }

    @Cacheable(key="#name")
    @Override
    public Map getPeopleInfosByName(String name){
        Map tempMap = new HashMap();
        List<PeopleInfos> peopleInfos = peopleInfosMapper.selectPeopleInfosByName(name);
        tempMap.put("code", 250);
        tempMap.put("infos", peopleInfos);
        return tempMap;

    }

    @CacheEvict(key="#name")
    @Override 
    public Map deletePeopleInfosByName(String name){
        Map tempMap = new HashMap();
        peopleInfosMapper.deletePeopleInfosByName(name);
        tempMap.put("code", 200);
        tempMap.put("infos", "删除成功");
        return tempMap;
    }

    @CachePut(key="#peopleInfos.name")
    @Override 
    public Map addPeopleInfos(PeopleInfos peopleInfos){
        Map tempMap = new HashMap();
        peopleInfosMapper.addPeopleInfos(peopleInfos);
        tempMap.put("code", 200);
        tempMap.put("infos", "添加成功");
        tempMap.put("datas", peopleInfos);
        return tempMap;
    }

    // @Override 
    // public Map getInfosByPlugin(Map params){
    //     Integer pageNum = Integer.parseInt(params.get("pageNum").toString());
    //     Integer pageSize = Integer.parseInt(params.get("pageSize").toString());
    //     logger.info("pageNum:{}", pageNum);
    //     PageHelper.startPage(pageNum, pageSize);
    //     List<PeopleInfos> infosLi = peopleInfosMapper.selectAllPeopleInfos();
    //     logger.info("infos:{}", infosLi);
    //     Page infosPage = (Page)infosLi;
    //     PageInfo<PeopleInfos> pageInfos = new PageInfo<PeopleInfos>(infosPage);
    //     return pageInfos;
    // }

}