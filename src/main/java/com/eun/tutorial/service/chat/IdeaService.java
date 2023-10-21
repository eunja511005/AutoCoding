package com.eun.tutorial.service.chat;

import java.util.List;

import com.eun.tutorial.dto.chat.ChatListRequest;
import com.eun.tutorial.dto.chat.ChatMessage;
import com.eun.tutorial.dto.chat.IdeaDTO;
import com.eun.tutorial.dto.chat.IdeaListRequest;

public interface IdeaService {
	List<IdeaDTO> getIdeaList(IdeaListRequest ideaListRequest);
	long getTotalIdeas();
	int saveIdea(IdeaDTO ideaDTO);
	int updateIdea(IdeaDTO ideaDTO);
	int deleteIdea(String id, String username);
	IdeaDTO getIdeaById(String id);
}