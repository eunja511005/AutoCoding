package com.eun.tutorial.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tika.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.ZthhBoardDTO;
import com.eun.tutorial.mapper.ZthhBoardMapper;
import com.eun.tutorial.service.user.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ZthhBoardServiceImpl implements ZthhBoardService {

	private final ZthhBoardMapper zthhBoardMapper;

    @Override
    public int save(ZthhBoardDTO zthhBoardDTO) {
    	if(StringUtils.isBlank(zthhBoardDTO.getBoardId())) {
    		UUID uuid = UUID.randomUUID();
    		String boardId = "board_"+uuid;
    		zthhBoardDTO.setBoardId(boardId);
    	}
        
        // 1. insert EMPTY_CLOB()
        zthhBoardMapper.save(zthhBoardDTO);
    	
    	
        // 2. update 실제 content
    	return zthhBoardMapper.save(zthhBoardDTO);
    }
    
    @Override
    public int update(ZthhBoardDTO zthhBoardDTO) {
    	if(StringUtils.isBlank(zthhBoardDTO.getBoardId())) {
    		UUID uuid = UUID.randomUUID();
    		String boardId = "board_"+uuid;
    		zthhBoardDTO.setBoardId(boardId);
    	}
        
        // 1. title, secret 업데이트
        zthhBoardMapper.update(zthhBoardDTO);
    	
    	
        // 2. content 업데이트
    	return zthhBoardMapper.save(zthhBoardDTO);
    }

    @Override
    public List<ZthhBoardDTO> findAll() {
        return zthhBoardMapper.findAll();
    }

	@Override
	public ZthhBoardDTO getOne(Map<String, Object> map) {
		return zthhBoardMapper.getOne(map);
	}

	@Override
	public Map<String, Object> delete(String boardId, UserDetailsImpl userDetailsImpl) {
    	Map<String, Object> res = new HashMap<>();
    	
	    Collection<? extends GrantedAuthority> authorities = userDetailsImpl.getAuthorities();
	    boolean isAdmin = authorities.stream()
	            .anyMatch(auth -> auth.getAuthority().equals("ROLE_SYS"));
	    
	    ZthhBoardDTO zthhBoardDTO = zthhBoardMapper.findById(boardId);
	    
	    // 자신의 글이거나 Admin만 삭제 가능
		if(userDetailsImpl.getUsername().equals(zthhBoardDTO.getCreateId()) || isAdmin) {
			zthhBoardMapper.delete(boardId);
			res.put("result", "delete success");
			res.put("redirectUrl", "/board/listForm");
			return res;
		}
    	
    	res.put("result", "No Authorization");
    	res.put("redirectUrl", null);
        
		return res;
	}

	@Override
	public Map<String, Object> findById(String boardId, UserDetailsImpl userDetailsImpl) {
		Map<String, Object> res = new HashMap<>();
		
		ZthhBoardDTO zthhBoardDTO = zthhBoardMapper.findById(boardId);
		
		if(zthhBoardDTO.isSecret()) {
			
		    Collection<? extends GrantedAuthority> authorities = userDetailsImpl.getAuthorities();
		    boolean isAdmin = authorities.stream()
		            .anyMatch(auth -> auth.getAuthority().equals("ROLE_SYS"));
			
			if(userDetailsImpl.getUsername().equals(zthhBoardDTO.getCreateId()) || isAdmin) {
				res.put("result", "find secret success");
				res.put("boardList", zthhBoardDTO);
				return res;
			}

			res.put("result", "No Authorization");
			res.put("boardList", null);
			return res;
		}
		
		res.put("result", "find normal success");
		res.put("boardList", zthhBoardDTO);
		return res;
	}
}