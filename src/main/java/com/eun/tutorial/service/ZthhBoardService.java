package com.eun.tutorial.service;

import java.util.List;
import java.util.Map;

import com.eun.tutorial.dto.ZthhBoardDTO;
import com.eun.tutorial.service.user.PrincipalDetails;

public interface ZthhBoardService {
	int save(ZthhBoardDTO zthhBoard);
	List<ZthhBoardDTO> findAll();
	ZthhBoardDTO getOne(Map<String, Object> map);
	Map<String, Object> delete(String id, PrincipalDetails principalDetails);
	Map<String, Object> findById(String boardId, PrincipalDetails principalDetails);
	int update(ZthhBoardDTO zthhBoardDTO);
}