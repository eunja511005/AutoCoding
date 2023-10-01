package com.eun.tutorial.config.chat;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.eun.tutorial.dto.chat.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ChatWebSocketHandler implements WebSocketHandler {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	String token = getToken(session);
		sessions.put(token, session);
        log.info("afterConnectionEstablished Session Count: {}", sessions.size());
    }

    private String getToken(WebSocketSession session) {
    	String token = "";

    	URI uri = session.getUri();
    	if (uri != null) {
    	    String query = uri.getQuery();
    	    if (query != null && query.startsWith("token=")) {
    	        token = query.substring("token=".length());
    	    }
    	}
    	return token;
	}

	@Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 클라이언트로부터 메시지를 받아서 처리
		// JSON 문자열을 ChatMessage 객체로 변환
		String receivedMessage = message.getPayload().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessage chatMessage = objectMapper.readValue(receivedMessage, ChatMessage.class);

        
        log.info("handleMessage Session Count: {}", sessions.size());
        
        // 메시지를 특정 사용자에게 보내기
//        String targetUserId = "anonymous"; // 대상 사용자 ID
//        WebSocketSession targetSession = sessions.get(targetUserId);
//        if (targetSession != null && targetSession.isOpen()) {
//            TextMessage textMessage = new TextMessage(receivedMessage);
//            targetSession.sendMessage(textMessage);
//        }
        
        for (WebSocketSession webSocketSession : sessions.values()) {
        	webSocketSession.sendMessage(new TextMessage(receivedMessage));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션 연결이 닫힌 경우 세션을 제거합니다.
    	String token = getToken(session);
        sessions.remove(token);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 통신 에러 처리
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}