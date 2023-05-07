package com.eun.tutorial.service.main;

import java.util.List;
import java.util.Map;

import com.eun.tutorial.dto.main.PostDTO;
import com.eun.tutorial.dto.main.PostSearchDTO;

public interface PostService {
	List<PostDTO> searchPosts(PostSearchDTO postSearchDTO);
    int getTotalCount(PostSearchDTO postSearchDTO);
	Map<String, Object> save(PostDTO postDTO);
	Map<String, Object> delete(String id);
	Map<String, Object> findById(String id);
}

