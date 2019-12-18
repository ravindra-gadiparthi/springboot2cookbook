package org.cloudcafe.boot2.ms.social.app.chat;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfig {

    @Bean
    public HandlerMapping webSocketMapping(ChatService chatService,
                                           InBoundChatService inboundChatService,
                                           OutBoundChatService outBoundChatService) {

        Map<String, WebSocketHandler>
                webSocketHandlerMap = new HashMap<>();

        webSocketHandlerMap.put("/topic/comments.new", chatService);
        webSocketHandlerMap.put("/app/chatMessage.new", inboundChatService);
        webSocketHandlerMap.put("/topic/chatMessage.new", outBoundChatService);

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(10);

        handlerMapping.setUrlMap(webSocketHandlerMap);

        return handlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
