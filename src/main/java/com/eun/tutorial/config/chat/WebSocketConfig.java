package com.eun.tutorial.config.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	//config.enableSimpleBroker("/topic/chat", "/topic/book"); // "/topic/chat" 및 "/topic/book"을 구독하는 클라이언트에게 메시지 전달
    	config.enableSimpleBroker("/topic");  // "/topic" 주제에 속하는 모든 하위 주제에 대한 구독을 활성화
        config.setApplicationDestinationPrefixes("/app"); // "/app"으로 시작하는 목적지로 메시지 수신
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
        .addEndpoint("/ws-service")
        .setAllowedOriginPatterns("*")
        .withSockJS(); // 인터셉터 등록
    }
}
