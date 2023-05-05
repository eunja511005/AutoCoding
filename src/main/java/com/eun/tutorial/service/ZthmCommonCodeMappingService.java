package com.eun.tutorial.service;

import java.util.List;

import com.eun.tutorial.dto.ZthmCommonCodeMappingDTO;

public interface ZthmCommonCodeMappingService {
	int save(ZthmCommonCodeMappingDTO zthmCommonCodeDTO);
	List<ZthmCommonCodeMappingDTO> findAll();
	int delete(long codeMappingId);
	List<ZthmCommonCodeMappingDTO> findByCodeMappingName(String codemappingName);
}