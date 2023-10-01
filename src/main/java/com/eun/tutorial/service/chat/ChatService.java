package com.eun.tutorial.service.chat;

import java.util.List;

import com.eun.tutorial.dto.chat.ChatListRequest;
import com.eun.tutorial.dto.chat.ChatMessage;

public interface ChatService {
	List<ChatMessage> getChatList(ChatListRequest chatListRequest);
	long getTotalChatMessages(String roomId);
	int saveChatMessage(ChatMessage chatMessage);
	int updateChatMessage(ChatMessage chatMessage);
	int deleteChatMessage(String id);
	ChatMessage getChatMessageById(String id);
}