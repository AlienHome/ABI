package com.sb.controller;

import com.sb.po.PeopleInfos;
import com.sb.service.PeopleInfosService;
import com.sb.util.TimeGeneratorUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins="*", maxAge=3600)
@RestController 
@RequestMapping("/api") 
public class PeopleInfosController{
    static Logger logger = LoggerFactory.getLogger(PeopleInfosController.class);
    @Autowired 
    private PeopleInfosService peopleInfosService;
    @Autowired 
    private RedisTemplate redisTemplate;

    @RequestMapping(value="/test", method=RequestMethod.POST) 
    @ResponseBody 
    public Map<String, String> getInput(@RequestBody Map<String, String> infos){
        return infos;
    }


    @RequestMapping(value="/get-infos/all", method=RequestMethod.GET)
    @ResponseBody 
    public List<PeopleInfos> getAllInfos(){
        List<PeopleInfos> infos = peopleInfosService.getAllPeopleInfos();
        logger.info("get all infos");
        return infos;
    }

    @RequestMapping(value="/add-infos", method=RequestMethod.POST) 
    @ResponseBody 
    public Map addPeopleInfos(@RequestBody PeopleInfos peopleInfos){
        return peopleInfosService.addPeopleInfos(peopleInfos);
    }

    @RequestMapping(value="/delete-by-name", method=RequestMethod.POST) 
    @ResponseBody 
    public Map deletePeopleInfosByName(@RequestBody PeopleInfos peopleInfos){
        // String name = peopleInfos.get("name").toString();
        String name = peopleInfos.getName();
        return peopleInfosService.deletePeopleInfosByName(name);
    }

    @RequestMapping(value="/get-infos-by-name", method=RequestMethod.POST)
    @ResponseBody 
    public Map getInfosByName(@RequestBody PeopleInfos peopleInfos){
        Map tempMap = new HashMap();
        String name = peopleInfos.getName();
        boolean hasKey = redisTemplate.hasKey("people1");
        logger.info("key:{}", hasKey);
        String timeFormat = TimeGeneratorUtil.timeFormatGen("yyyy-MM-dd HH:mm:ss");
        tempMap.put("time", timeFormat);
        tempMap.put("infos", peopleInfosService.getPeopleInfosByName(name));
        return tempMap;
    }

    @RequestMapping(value="/get-headers/infos", method=RequestMethod.POST) 
    @ResponseBody 
    public Map<String, Object> getHeaderInfos(HttpServletRequest req){
        Map<String, Object> headerInfos = new HashMap<String, Object>();
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String nextElement = headerNames.nextElement();
            headerInfos.put(nextElement, req.getHeader(nextElement));
            System.out.format(nextElement+":"+req.getHeader(nextElement)+"\n");
        }
        System.out.format("header infos: "+headerInfos+"\n");
        System.out.format("============\n");
        headerInfos.put("URL", req.getRequestURL());
        headerInfos.put("URI", req.getRequestURI());
        // headerInfos.put("QueryString", req.getRequestQueryString());
        headerInfos.put("RemoteAddr", req.getRemoteAddr());
        headerInfos.put("RemoteHost", req.getRemoteHost());
        headerInfos.put("RemotePort", req.getRemotePort());
        headerInfos.put("LocalAddr", req.getLocalAddr());
        headerInfos.put("LocalName", req.getLocalName());
        headerInfos.put("PathInfo", req.getPathInfo());

        // get header by custom input 
        // String customHeader = req.getHeader("token");
        // headerInfos.put("token", customHeader);

        return headerInfos;
    }

    @RequestMapping(value="/get-url/query", params={"id", "name"}, method=RequestMethod.GET)
    @ResponseBody 
    public Map<String, Object> getUrlInfos(HttpServletRequest req, @RequestParam("id") Integer id, @RequestParam("name") String name){
        Map<String, Object> queryInfos = new HashMap<String, Object>();
        queryInfos.put("infos", req.getQueryString());
        queryInfos.put("id", id);
        queryInfos.put("name", name);
        return queryInfos;
    }

    @RequestMapping(value="/get-body/infos", method=RequestMethod.POST) 
    @ResponseBody
    public Map<String, String> getBodyInfos(@RequestBody Map<String, String> bodyInfos){
        return bodyInfos;
    }

    @RequestMapping(value="/set-response/infos", method=RequestMethod.GET) 
    @ResponseBody  
    public Map<String, Object> setResponseInfos(HttpServletResponse resp){
        Map<String, Object> responseInfos = new HashMap<String, Object>();
        resp.addHeader("testHeader", "addHeader");
        resp.setDateHeader("expires", 500);
        return responseInfos;
    }




}