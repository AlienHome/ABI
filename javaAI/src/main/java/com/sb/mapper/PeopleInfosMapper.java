package com.sb.mapper;

import java.util.List;
import java.util.Map;

import com.sb.po.PeopleInfos;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

// @Repository("peopleInfoDao")
// @Mapper
public interface PeopleInfosMapper{
    public List<PeopleInfos> selectAllPeopleInfos();
    public Integer addPeopleInfos(PeopleInfos peopleInfos);
    public Integer deletePeopleInfosByName(String name);
    public List<PeopleInfos> selectPeopleInfosByName(String name);
}