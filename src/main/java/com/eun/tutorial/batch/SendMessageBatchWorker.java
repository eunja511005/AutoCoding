package com.eun.tutorial.batch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.chat.ChatMessage;
import com.eun.tutorial.service.chat.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendMessageBatchWorker extends BatchWorker {
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final ChatService chatService;
	
    public void sendToTopic(String topic, String message) {
        simpMessagingTemplate.convertAndSend(topic, message);
    }

	public void doBatch() {
        SimpleDateFormat sdf = new SimpleDateFormat("a hh:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());
    	
    	ChatMessage chatMessage = new ChatMessage();
    	chatMessage.setRoomId("ROOM_001");
    	chatMessage.setSender("autoCoding");
    	chatMessage.setTimestamp(currentTime);
    	chatMessage.setMessage("안녕, 나는 매시 정각에 인사하는 스케쥴러야!");
    	
    	chatService.saveChatMessage(chatMessage); // 메시지 DB에 저장
    	
    	ObjectMapper objectMapper = new ObjectMapper();
        try {
			sendToTopic("/topic/chat", objectMapper.writeValueAsString(chatMessage));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
