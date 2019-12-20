package com.sb.controller;

import com.sb.po.PeopleInfos;
import com.sb.service.PeopleInfosService;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins="*", maxAge=3600)
@Controller
@RequestMapping("/api/ui")
public class UIController{
    @Autowired 
    private PeopleInfosService peopleInfosService;
    static Logger logger = LoggerFactory.getLogger(UIController.class);
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String loginSys(){
        return "sys/login";
    }

    @RequestMapping(value="/index", method=RequestMethod.POST)
    public String indexSys(@RequestParam("userName") String username, @RequestParam("passWord") String password, Model model){
        Map<String, String> loginInfos = new HashMap();
        loginInfos.put("username", username);
        loginInfos.put("password", password);
        logger.info("username:{}, password:{}", username, password);
        model.addAttribute("loginInfos", loginInfos);
        model.addAttribute("peopleInfos", peopleInfosService.getAllPeopleInfos());
        return "sys/index";
    }
}
