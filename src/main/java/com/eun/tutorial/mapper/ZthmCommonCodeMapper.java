package com.eun.tutorial.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.ZthmCommonCodeDTO;

@Mapper
public interface ZthmCommonCodeMapper {
	int save(ZthmCommonCodeDTO zthmCommonCodeDTO);
	List<ZthmCommonCodeDTO> findAll();
	ZthmCommonCodeDTO findOne(Map<String, Object> map);
	int delete(String id);
	List<ZthmCommonCodeDTO> findByGroupId(String string);
}
