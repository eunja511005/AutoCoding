package com.eun.tutorial.controller.chat;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.config.chat.BookMessage;
import com.eun.tutorial.config.chat.ChatMessage;

@RestController
public class ChatController {

    private final Queue<DeferredResult<ChatMessage>> chatQueue = new LinkedList<>();
    
	@GetMapping("/chat/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/chat");
		return modelAndView;
	}
	
    @MessageMapping("/chat/send")
    @SendTo("/topic/chat")
    public ChatMessage sendChatMessage(@Payload ChatMessage message) {
        // 받은 메시지를 "/topic/chat" 주제로 브로드캐스팅하여 모든 클라이언트에게 전송
        return message;
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

