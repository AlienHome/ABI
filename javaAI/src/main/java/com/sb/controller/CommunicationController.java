// package com.sb.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.RequestMapping;

// // import java.security.Principal;

// import com.sb.po.Message;
// import com.sb.po.Chat;

// @CrossOrigin(origins="*", maxAge=3600)
// // @RequestMapping("/app")
// @Controller
// public class CommunicationController{
//     @Autowired
//     SimpMessagingTemplate messagingTemplate;
//     // @RequestMapping("/hello")
//     @MessageMapping("/hello")
//     @SendTo("/topic/greetings")
//     public Message greeting(Message message) throws Exception{
//         return message;
//     }

//     // @MessageMapping("/chat")
//     // public void chat(Principal principal, Chat chat){
//     //     String from = principal.getName();
//     //     chat.setFrom(from);
//     //     messagingTemplate.convertAndSendToUser(chat.getTo(),"/queue/chat", chat);
//     // }
// }
