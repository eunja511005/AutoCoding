package com.eun.tutorial.mapper.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.eun.tutorial.dto.chat.ChatMessage;

@Mapper
public interface ChatMapper {
	List<ChatMessage> selectChatList(@Param("offset") int offset, @Param("limit") int limit, @Param("roomId") String roomId);
	long selectTotalChatMessages(@Param("roomId") String roomId);
	int insertChat(ChatMessage chatMessage);
	int updateChat(ChatMessage chatMessage);
	int deleteChat(String id);
	ChatMessage getChatMessageById(String id);
}
