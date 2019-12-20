package com.sb.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthentaticationController{
    @GetMapping("/admin/hello") 
    public String admin(){
        return "hello admin";
    }

    @GetMapping("/user/hello")
    public String user(){
        return "hello user";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @PostMapping("/post-test")
    public Map<String, String> postTest(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xiaohong");
        map.put("address", "hegang");
        return map; 
    }
}