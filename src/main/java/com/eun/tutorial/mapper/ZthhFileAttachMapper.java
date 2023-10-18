package com.eun.tutorial.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.dto.main.PostDTO;

@Mapper
public interface ZthhFileAttachMapper {
	int save(ZthhFileAttachDTO zthmError);
	List<ZthhFileAttachDTO> findAll();
	List<ZthhFileAttachDTO> selectFiles(Map<String, Object> params);
	List<ZthhFileAttachDTO> selectFileListByAttachId(@Param("attachId") String attachId);
	ZthhFileAttachDTO getOne(Map<String, Object> map);
}
