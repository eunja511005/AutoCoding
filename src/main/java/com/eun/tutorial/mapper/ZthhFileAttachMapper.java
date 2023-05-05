package com.eun.tutorial.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.ZthhFileAttachDTO;

@Mapper
public interface ZthhFileAttachMapper {
	int save(ZthhFileAttachDTO zthmError);
	List<ZthhFileAttachDTO> findAll();
	ZthhFileAttachDTO getOne(Map<String, Object> map);
}
