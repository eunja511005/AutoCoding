package com.eun.tutorial.controller.chat;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.config.chat.BookMessage;
import com.eun.tutorial.dto.chat.ChatListRequest;
import com.eun.tutorial.dto.chat.ChatListResponse;
import com.eun.tutorial.dto.chat.ChatMessage;
import com.eun.tutorial.service.chat.ChatService;
import com.eun.tutorial.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    
	@GetMapping("/chat/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/chat");
		return modelAndView;
	}
	
	@PostMapping("/api/chat/list")
    public @ResponseBody ChatListResponse getChatList(@RequestHeader("Authorization") String authToken, @RequestBody ChatListRequest chatListRequest) {
		String token = authToken.substring(7); // "Bearer " 이후의 토큰 부분 추출
    	
    	if (JwtTokenUtil.validateToken(token)) {
    		List<ChatMessage> messages = chatService.getChatList(chatListRequest);
    		long totalElements = chatService.getTotalChatMessages(chatListRequest.getRoomId());
    		return new ChatListResponse(true, messages, totalElements);
    	}else {
    		 // 토큰이 유효하지 않은 경우 예외 처리
            throw new IllegalArgumentException("Invalid token");
    	}
    }
	
    @MessageMapping("/chat/send")
    @SendTo("/topic/chat")
    public ChatMessage sendChatMessage(@Payload ChatMessage message) {
    	chatService.saveChatMessage(message); // 받은 메시지 DB에 저장
        return message; // 받은 메시지를 "/topic/chat" 주제로 브로드캐스팅하여 모든 클라이언트에게 전송
    }

    @MessageMapping("/book/send")
    @SendTo("/topic/book")
    public BookMessage sendBookMessage(@Payload BookMessage message) {
        // 받은 메시지를 "/topic/book" 주제로 브로드캐스팅하여 모든 클라이언트에게 전송
        return message;
    }

//    @GetMapping("/subscribe")
//    public DeferredResult<ChatMessage> subscribeToChat() {
//        // 클라이언트의 요청을 대기열에 추가
//        DeferredResult<ChatMessage> deferredResult = new DeferredResult<>();
//        chatQueue.add(deferredResult);
//        return deferredResult;
//    }
//
//    @PostMapping("/publish")
//    public void publishChatMessage(@RequestBody ChatMessage message) {
//        // 대기열에 있는 모든 클라이언트에게 메시지 전달
//        while (!chatQueue.isEmpty()) {
//            DeferredResult<ChatMessage> deferredResult = chatQueue.poll();
//            deferredResult.setResult(message);
//        }
//    }
}

