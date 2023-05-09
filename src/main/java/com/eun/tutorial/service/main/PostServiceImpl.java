package com.eun.tutorial.service.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tika.utils.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.main.CommentDTO;
import com.eun.tutorial.dto.main.PostDTO;
import com.eun.tutorial.dto.main.PostSearchDTO;
import com.eun.tutorial.mapper.main.PostMapper;
import com.eun.tutorial.service.user.UserDetailsImpl;

@Service
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;

    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

	@Override
	public List<PostDTO> searchPosts(PostSearchDTO postSearchDTO) {
		Map<String, Object> params = new HashMap<>();
		params.put("offset", postSearchDTO.getStart());
		params.put("limit", postSearchDTO.getLength());
		params.put("orderColumnName", postSearchDTO.getOrderColumnName());
		params.put("orderDirection", postSearchDTO.getOrderDirection());
		params.put("search", postSearchDTO.getSearch());

		return postMapper.selectPosts(params);
	}

	@Override
	public int getTotalCount(PostSearchDTO postSearchDTO) {
		Map<String, Object> params = new HashMap<>();
		params.put("search", postSearchDTO.getSearch());
		
		return postMapper.getTotalCount(params);
	}

	@Override
	public Map<String, Object> save(PostDTO postDTO) {
		Map<String, Object> res = new HashMap<>();

		if(StringUtils.isBlank(postDTO.getId())) {
    		UUID uuid = UUID.randomUUID();
    		String postId = "post_"+uuid;
    		postDTO.setId(postId);
    		
            // 1. insert EMPTY_CLOB()
        	postMapper.mergePost(postDTO);
        	
            // 2. update 실제 content
        	postMapper.mergePost(postDTO);
        	
        	res.put("result", "A new forum has been created.");
    	}else {
            // 1. title, secret 업데이트
    		postMapper.update(postDTO);
        	
            // 2. content 업데이트
        	postMapper.mergePost(postDTO);
        	
        	res.put("result", "A forum has been changed.");
    	}
        

		return res;
	}

	@Override
	public Map<String, Object> delete(String id) {
		Map<String, Object> res = new HashMap<>();
		postMapper.delete(id);
		res.put("result", "delete success");
		res.put("redirectUrl", "/posts/list");
		return res;
	}

	@Override
	public Map<String, Object> findById(String id) {
		Map<String, Object> res = new HashMap<>();
		PostDTO postDTO = postMapper.findById(id);
		res.put("result", "find forum success");
		res.put("postList", postDTO);
		return res;
	}

	@Override
	public Map<String, Object> addComment(CommentDTO commentDTO) {
		Map<String, Object> res = new HashMap<>();
		postMapper.mergeComment(commentDTO);
		res.put("result", "comment save success");
		return res;
	}

	@Override
	public Map<String, Object> findCommentsById(String id, Authentication authentication) {
		Map<String, Object> res = new HashMap<>();
		
		String currentUserId = "";
		if(authentication!=null) {
			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
			currentUserId = userDetailsImpl.getUsername();
		}
		
		List<CommentDTO> commentDTO = postMapper.findCommentsById(id);
		res.put("currentUserId", currentUserId);
		res.put("commentList", commentDTO);
		return res;
	}

	@Override
	public Map<String, Object> deleteComment(String id, Authentication authentication) {
		Map<String, Object> res = new HashMap<>();
		
		String currentUserId = "";
		if(authentication!=null) {
			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
			currentUserId = userDetailsImpl.getUsername();
		}
		
		postMapper.deleteComment(id, currentUserId);
		res.put("result", "delete success");
		return res;
	}


}


