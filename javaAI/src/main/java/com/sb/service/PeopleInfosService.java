package com.sb.service;

import com.sb.po.PeopleInfos;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface PeopleInfosService{
    public List<PeopleInfos> getAllPeopleInfos();
    public Map addPeopleInfos(PeopleInfos peopleInfos);
    public Map deletePeopleInfosByName(String name);
    public Map getPeopleInfosByName(String name);
    // public PageInfo<PeopleInfos> getInfosByPlugin(Map params);
}