package com.sb.config;

import com.sb.MsgSocketHandle;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * @Description 
 * @Author Nobody
 * @Date 2019/10/18 14:19
 * @Version 1.0
 **/
@Component
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig_backup implements WebMvcConfigurer, WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        System.out.println("===========================注册websocket================\n");
        webSocketHandlerRegistry.addHandler(msgSocketHandle(),"/websocket")
                .addInterceptors(new WebSocketHandshakeInterceptor());
        webSocketHandlerRegistry.addHandler(msgSocketHandle(),
                "/sockjs/websocket").
                addInterceptors(new WebSocketHandshakeInterceptor())
                .withSockJS();
    }

    @Bean
    public WebSocketHandler msgSocketHandle(){
        return new MsgSocketHandle();
    }
}