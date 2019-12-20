package com.sb;
 
import com.sb.po.People;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
/**
 * @Description 
 * @Author Nobody
 * @Date 2019/10/18 14:
 * @Version 1.0
 **/
@Component
public class MsgSocketHandle implements WebSocketHandler {
    /**已经连接的用户*/
    private static final List<WebSocketSession> peoples;
    static {
        //保存当前连接用户
        peoples = new ArrayList<>();
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        //将用户信息添加到list中
        peoples.add(webSocketSession);
        System.out.println("=====================建立连接成功==========================\n");
        People people  = (People) webSocketSession.getAttributes().get("people");
        if(people != null){
            System.out.println("当前连接用户:"+people.getUserName()+"\n");
        }
        System.out.println("webSocket连接数量:"+peoples.size()+"\n");
    }
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        People people = (People) webSocketSession.getAttributes().get("people");
        System.out.println("收到用户:"+people.getUserName()+"的消息\n");
    }
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()){
            //关闭session
            try {
                webSocketSession.close();
            } catch (IOException e) {
            }
        }
        //移除用户
        peoples.remove(webSocketSession);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        peoples.remove(webSocketSession);
        People people = (People) webSocketSession.getAttributes().get("people");
        System.out.println(people.getUserName()+"断开连接\n");
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    public void sendMessageToUser(People people, TextMessage messageInfo){
        for (WebSocketSession session : peoples) {
            People sessionUser = (People) session.getAttributes().get("people");
            //根据用户名去判断用户接收消息的用户
            if(people.getUserName().equals(sessionUser.getUserName())){
                try {
                    if (session.isOpen()){
                        session.sendMessage(messageInfo);
                        System.out.println("发送消息给："+people.getUserName()+"内容："+messageInfo+"\n");
                    }
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void sendMsgToAllUsers(TextMessage messageInfo) throws Exception{
        for (WebSocketSession user : peoples) {
            user.sendMessage(messageInfo);
        }
    }
}