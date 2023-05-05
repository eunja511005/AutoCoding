package com.eun.tutorial.service;

import java.util.List;
import java.util.Map;

import com.eun.tutorial.dto.ZthhBoardDTO;
import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.dto.ZthmCommonCodeDTO;

public interface ZthmCommonCodeService {
	int save(ZthmCommonCodeDTO zthmCommonCodeDTO);
	List<ZthmCommonCodeDTO> findAll();
	ZthmCommonCodeDTO findOne(Map<String, Object> map);
	int delete(String id);
	Map<String, Object> findByGroupId();
}