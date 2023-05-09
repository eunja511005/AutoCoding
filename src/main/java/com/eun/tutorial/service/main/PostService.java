package com.eun.tutorial.service.main;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;

import com.eun.tutorial.dto.main.CommentDTO;
import com.eun.tutorial.dto.main.PostDTO;
import com.eun.tutorial.dto.main.PostSearchDTO;
import com.eun.tutorial.service.user.UserDetailsImpl;

public interface PostService {
	List<PostDTO> searchPosts(PostSearchDTO postSearchDTO);
    int getTotalCount(PostSearchDTO postSearchDTO);
	Map<String, Object> save(PostDTO postDTO);
	Map<String, Object> delete(String id);
	Map<String, Object> findById(String id);
	Map<String, Object> addComment(CommentDTO commentDTO);
	Map<String, Object> findCommentsById(String id, Authentication authentication);
	Map<String, Object> deleteComment(String id, Authentication authentication);
}

