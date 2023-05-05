package com.eun.tutorial.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.ZthhBoardDTO;
import com.eun.tutorial.dto.ZthhFileAttachDTO;

@Mapper
public interface ZthhBoardMapper {
	int save(ZthhBoardDTO zthhBoard);
	List<ZthhBoardDTO> findAll();
	ZthhBoardDTO getOne(Map<String, Object> map);
	int delete(String id);
	ZthhBoardDTO findById(String boardId);
	int update(ZthhBoardDTO zthhBoardDTO);
}
