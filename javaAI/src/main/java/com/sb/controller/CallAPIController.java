package com.sb.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import com.sb.util.HttpClientUtil;
import com.sb.util.https.HttpsClientV45Util;
// import com.sb.util.HttpsClientUtil;
import com.sb.util.https.HttpsTrustClientV45;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.http.client.HttpClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
// import org.apache.http.client.HttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api")
public class CallAPIController{
    @RequestMapping(value="/call/test-get", method=RequestMethod.POST)
    public Map testGet(@RequestBody Map infos){
        if(infos.containsKey("name") && infos.containsKey("sex")){
            String url = "http://127.0.0.1:8091/test-get";
            String str = HttpClientUtil.doGet(url, infos, null);
            Map outInfos = (Map)JSONObject.fromObject(str);
            return outInfos;
        }else{
            Map errorMap = new HashMap(){
                {
                    put("code", 400);
                    put("infos","请检查输入参数");
                }
            };
            return errorMap;
        }   
    }

    @RequestMapping(value="/call/test-post", method=RequestMethod.POST)
    public Map testPost(@RequestBody Map infos){
        if(infos.containsKey("name")&&infos.containsKey("sex")){
            String url="http://127.0.0.1:8091/test-json";
            // string json
            String param = JSONObject.fromObject(infos).toString();
            String str = HttpClientUtil.doPostJson(url, param);
            Map outInfos = (Map)JSONObject.fromObject(str);
            return outInfos;
        }else{
            Map errorMap = new HashMap(){
                {
                    put("code", 400);
                    put("infos", "输入参数有误,请检查输入参数");
                }
            };
            return errorMap;
        }
    }

    @RequestMapping(value="/call/token-get", method=RequestMethod.POST)
    public Map getToken(@RequestBody Map params, HttpServletRequest req){
        Map<String, String> paramHeader = new HashMap<String, String>();
        paramHeader.put("Accept", "application/json");
        String url = "https://ip:port/v3/auth/tokens";
        String strJson = JSONObject.fromObject(params).toString();
        String result = null;
        System.out.format("str json:"+strJson+"\n");
        HttpClient httpClient = null;
        try{
            httpClient = new HttpsTrustClientV45().init();
            result = HttpsClientV45Util.doPostGetHeader(httpClient, url, paramHeader, strJson);
            // System.out.format("result:"+result+"\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        Map token = new HashMap();
        token.put("code", 200);
        token.put("token", result);
        // token.put("token", req.getHeader("X-Subject-Token"));
        // return returnBody;
        return token;
    }

    

}