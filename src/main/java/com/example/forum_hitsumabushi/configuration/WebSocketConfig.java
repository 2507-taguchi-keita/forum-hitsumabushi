package com.example.forum_hitsumabushi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");   // 送信先のプレフィックス
        config.setApplicationDestinationPrefixes("/app"); // クライアント送信用プレフィックス
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // クライアント接続用エンドポイント
        registry.addEndpoint("/ws").withSockJS();
    }
}
