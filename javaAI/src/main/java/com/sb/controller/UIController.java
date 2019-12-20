package com.sb.controller;

import com.sb.po.PeopleInfos;
import com.sb.service.PeopleInfosService;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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

    @RequestMapping(value="/upload", method=RequestMethod.GET) 
    public String uploadFile(){
        return "/fileUpDownLoad/uploadFiles";
    }

    @RequestMapping(value="/single-file-upload", method=RequestMethod.POST) 
    @ResponseBody
    public Map uploadSingleFile(@RequestParam("file") MultipartFile file){
        Map returnMap = new HashMap();
        if(file.isEmpty()){
            returnMap.put("code", 400);
            returnMap.put("infos", "上传失败,请选择文件");
            return returnMap;
        }
        String fileName = file.getOriginalFilename();
        String filePath = "/home/xdq/xinPrj/java/javaWebTest/pureBackendSB/javaAI/src/main/resources/file/";
        File fileObj = new File(filePath+fileName);
        try{
            file.transferTo(fileObj);
            returnMap.put("code", 200);
            returnMap.put("infos", "上传成功");
            return returnMap;
        }catch (IOException e){
            e.printStackTrace();
        }
        returnMap.put("code", 400);
        returnMap.put("infos", "上传失败,请选择文件");
        return returnMap;
    }

    @RequestMapping(value="/multi-file-upload", method=RequestMethod.POST) 
    @ResponseBody 
    public Map uploadMultiFile(HttpServletRequest req){
        Map returnMap = new HashMap();
        List<MultipartFile> files = ((MultipartHttpServletRequest) req).getFiles("file");
        String filePath = "/home/xdq/xinPrj/java/javaWebTest/pureBackendSB/javaAI/src/main/resources/file/";
        for(int i=0;i<files.size();i++){
            MultipartFile file = files.get(i);
            if(file.isEmpty()){
                returnMap.put("code", 201);
                returnMap.put("infos", "上传第"+String.valueOf(i+1)+"个文件失败");
                return returnMap;
            }
            String fileName = file.getOriginalFilename();
            File fileObj = new File(filePath+fileName);
            try{
                file.transferTo(fileObj);
            }catch (IOException e){
                returnMap.put("code", 201);
                returnMap.put("infos", "上传第"+String.valueOf(i++)+"个文件失败");
                return returnMap;
            }
        }
        returnMap.put("code", 200);
        returnMap.put("infos", "上传第"+String.valueOf(i+1)+"个文件失败");
        return returnMap;
    }
}
