package com.eun.tutorial.mapper.main;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.main.CommentDTO;
import com.eun.tutorial.dto.main.PostDTO;

@Mapper
public interface PostMapper {
    List<PostDTO> selectPosts(Map<String, Object> params);
	int getTotalCount(Map<String, Object> params);
	int mergePost(PostDTO postDTO);
	int delete(String id);
	PostDTO findById(String id);
	int update(PostDTO postDTO);
	int mergeComment(CommentDTO commentDTO);
	List<CommentDTO> findCommentsById(String id);
	int deleteComment(String id, String currentUserId);
}


