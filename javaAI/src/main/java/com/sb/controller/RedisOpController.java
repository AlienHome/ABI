package com.sb.controller;

import com.sb.po.PeopleInfos;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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

@CrossOrigin(origins="*", maxAge=3600) 
@Controller 
@RequestMapping("/api/redis")
public class RedisOpController{
    @Autowired 
    private StringRedisTemplate stringRedisTemplate;
    @Autowired 
    private RedisTemplate redisTemplate;

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody 
    public Map addDataToRedis(@RequestBody Map infos){
        Map tempMap = new HashMap();
        try{
            if(infos.containsKey("username")&&infos.containsKey("password")&&infos.containsKey("address")&&infos.containsKey("objname")){
            
                String username = infos.get("username").toString();
                String password = infos.get("password").toString();
                String address = infos.get("address").toString();
                String objname = infos.get("objname").toString();
                stringRedisTemplate.opsForValue().set("username", username, 10, TimeUnit.SECONDS);
                stringRedisTemplate.opsForValue().set("password", password, 15, TimeUnit.SECONDS);
                // stringRedisTemplate.opsForValue().set("username", username);
                // stringRedisTemplate.opsForValue().set("password", password);
                PeopleInfos peopleInfos = new PeopleInfos();
                peopleInfos.setName(username);
                peopleInfos.setAddress(address);
                redisTemplate.opsForValue().set(objname, peopleInfos, 15, TimeUnit.SECONDS);
                tempMap.put("code", 200);
                tempMap.put("infos", "添加成功");
                return tempMap;
            }else{
                tempMap.put("code", 400);
                tempMap.put("infos", "检查参数");
                return tempMap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tempMap; 
    }

    @RequestMapping(value="/delete")
    @ResponseBody 
    public Map deleteByKey(@RequestBody Map infos){
        Map tempMap = new HashMap();
        try{
            if(infos.containsKey("username")&&infos.containsKey("password")&&infos.containsKey("objname")){
                String username = infos.get("username").toString();
                String password = infos.get("password").toString();
                String objname = infos.get("objname").toString();
                stringRedisTemplate.delete(username);
                stringRedisTemplate.delete(password);
                redisTemplate.delete(objname);
                tempMap.put("code", 200);
                tempMap.put("infos", "删除成功");
            }else{
                tempMap.put("code", 400);
                tempMap.put("infos", "检查参数");

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tempMap;
    }

    @RequestMapping(value="/search/obj", method=RequestMethod.POST) 
    @ResponseBody 
    public PeopleInfos searchDataInRedis(@RequestBody Map infos){
        PeopleInfos peopleInfos = new PeopleInfos();
        try{
            if(infos.containsKey("objname")){
                String objname = infos.get("objname").toString();
                peopleInfos = (PeopleInfos) redisTemplate.opsForValue().get(objname);
                redisTemplate.opsForValue().set("userInfosObj", peopleInfos);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return peopleInfos;
    }

    @RequestMapping(value="/search/string", method=RequestMethod.POST) 
    @ResponseBody 
    public Map searchDataInRedisString(@RequestBody Map infos){
        Map infosOut = new HashMap();
        String usernameR, passwordR;
        try{
            if(infos.containsKey("username")&&infos.containsKey("password")){
                String username = infos.get("username").toString();
                String password = infos.get("password").toString();
                usernameR = stringRedisTemplate.opsForValue().get(username);
                passwordR = stringRedisTemplate.opsForValue().get(password);
                infosOut.put("username", usernameR);
                infosOut.put("password", passwordR);
                
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return infosOut;
    }


}
