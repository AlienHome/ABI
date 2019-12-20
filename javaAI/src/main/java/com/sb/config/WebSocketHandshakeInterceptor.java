package com.sb.config;
 
import com.sb.po.People;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
 
import javax.servlet.http.HttpSession;
import java.util.Map;
 
/**
 * @Description 
 * @Author Nobody
 * @Date 2019/10/18 14:19
 * @Version 1.0
 **/
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        System.out.format("before handshake\n");
        if (request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletServerHttpRequest.getServletRequest().getSession(false);
            if(session != null){
                //从session中获取当前用户
                People people = (People) session.getAttribute("people");
                map.put("people",people);
            }
        }
        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.format("after handshake\n");
        super.afterHandshake(serverHttpRequest,serverHttpResponse,webSocketHandler,e);
    }
}