package com.eun.tutorial.batch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.chat.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@EnableAsync
public class SendMessageScheduler {
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
    public void sendToTopic(String topic, String message) {
        simpMessagingTemplate.convertAndSend(topic, message);
    }
    
    
    //@Scheduled(fixedRate = 1 * 60 * 1000) // 1분 단위로 예약된 메시지 보내기
    @Scheduled(cron = "0 0 * * * ?") // 매시 정각에 수행
    public void sendScheduledMessage() throws JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("a hh:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());
    	
    	ChatMessage chatMessage = new ChatMessage();
    	chatMessage.setRoomId("ROOM_001");
    	chatMessage.setSender("autoCoding");
    	chatMessage.setTimestamp(currentTime);
    	chatMessage.setMessage("안녕, 나는 매시 정각에 인사하는 스케쥴러야!");
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
        sendToTopic("/topic/chat", objectMapper.writeValueAsString(chatMessage));
    }
}
