package com.sb.controller;
 
import com.sb.po.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.TextMessage;
import javax.servlet.http.HttpServletRequest;

// import com.websocket.fullversion.MsgSocketHandle;
import com.sb.MsgSocketHandle;

import java.util.Map;
import java.util.HashMap;
 
@RequestMapping("/websocket")
@Controller
public class MsgController {
    @Autowired
    private MsgSocketHandle msgSocketHandle;
    @RequestMapping("/index")
    public String login(){
        return "websocket/login";
    }

    @RequestMapping(value="/view", method=RequestMethod.GET)
    public String getView(){
        return "websocket/chat";
    }

    @RequestMapping(value="/test", method=RequestMethod.GET)
    @ResponseBody 
    public Map<String, String> testApi(){
        Map<String, String> infos = new HashMap<String, String>();
        infos.put("name", "xioahong");
        infos.put("address", "hefei");
        return infos;
    }

    @RequestMapping("/login")
    public String login(People people, HttpServletRequest request){
        System.out.format("name:"+people.getUserName()+"\n");
        request.getSession().setAttribute("people",people);
        return "websocket/chat";
    }
    @RequestMapping(value="/login/by-post", method=RequestMethod.GET)
    // @ResponseBody
    public String loginByPost(@RequestBody Map<String, String> userInfos, HttpServletRequest request){
    // public Map<String, String> loginByPost(@RequestBody Map<String, String> userInfos, HttpServletRequest request){
        request.getSession().setAttribute("people", userInfos);
        System.out.format("user login\n");
        // return userInfos;
        return "websocket/chat";
    }

    @ResponseBody
    @RequestMapping(value = "/sendMsg",produces = "application/json; charset=utf-8")
    public String sendMsgToUser(String content,String toUserName){
        People people = new People();
        people.setUserName(toUserName);
        TextMessage textMessage = new TextMessage(content);
        msgSocketHandle.sendMessageToUser(people,textMessage);
        return "发送成功";
    }
    @ResponseBody
    @RequestMapping(value = "/sendMsg2",produces = "application/json; charset=utf-8")
    public String sendMsgToAll(String content) throws Exception {
        TextMessage textMessage = new TextMessage(content);
        msgSocketHandle.sendMsgToAllUsers(textMessage);
        return "发送成功";
    }
}