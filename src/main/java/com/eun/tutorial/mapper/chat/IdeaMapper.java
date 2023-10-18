package com.eun.tutorial.mapper.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.eun.tutorial.dto.chat.ChatMessage;
import com.eun.tutorial.dto.chat.IdeaDTO;

@Mapper
public interface IdeaMapper {
	List<IdeaDTO> selectIdeaList(@Param("offset") int offset, @Param("limit") int limit);
	long selectTotalIdeas();
	int insertIdea(IdeaDTO ideaDTO);
	int updateIdea(IdeaDTO ideaDTO);
	int deleteIdea(String id);
	ChatMessage getIdeaById(String id);
}
