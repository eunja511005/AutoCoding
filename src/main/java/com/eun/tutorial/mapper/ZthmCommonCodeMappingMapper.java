package com.eun.tutorial.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.ZthmCommonCodeDTO;
import com.eun.tutorial.dto.ZthmCommonCodeMappingDTO;

@Mapper
public interface ZthmCommonCodeMappingMapper {
	int save(ZthmCommonCodeMappingDTO zthmCommonCodeDTO);
	List<ZthmCommonCodeMappingDTO> findAll();
	int delete(long codeMappingId);
	List<ZthmCommonCodeMappingDTO> findByCodeMappingName(String codemappingName);
}
