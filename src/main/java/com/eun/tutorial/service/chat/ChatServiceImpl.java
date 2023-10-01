package com.eun.tutorial.service.chat;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.chat.ChatListRequest;
import com.eun.tutorial.dto.chat.ChatMessage;
import com.eun.tutorial.mapper.chat.ChatMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService{
	private final ChatMapper chatMapper;
	
	@Override
	public List<ChatMessage> getChatList(ChatListRequest request) {
		int offset = (request.getPage() - 1) * request.getSize();
		return chatMapper.selectChatList(offset, request.getSize(), request.getRoomId());
	}

	@Override
	public int saveChatMessage(ChatMessage chatMessage) {
		chatMessage.setId("CHAT_"+UUID.randomUUID());
		return chatMapper.insertChat(chatMessage);
	}

	@Override
	public int updateChatMessage(ChatMessage chatMessage) {
		return chatMapper.updateChat(chatMessage);
	}

	@Override
	public int deleteChatMessage(String id) {
		return chatMapper.deleteChat(id);
	}

	@Override
	public ChatMessage getChatMessageById(String id) {
		return getChatMessageById(id);
	}

	@Override
	public long getTotalChatMessages(String roomId) {
		return chatMapper.selectTotalChatMessages(roomId);
	}

}
