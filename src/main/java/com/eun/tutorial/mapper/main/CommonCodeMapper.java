package com.eun.tutorial.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.main.CommonCodeDTO;

@Mapper
public interface CommonCodeMapper {

	List<CommonCodeDTO> selectCommonCodeList();
	int insertCommonCode(CommonCodeDTO commonCodeDTO);
	int updateCommonCode(CommonCodeDTO commonCodeDTO);
	int deleteCommonCode(Long id);
	CommonCodeDTO getCommonCodeListById(String id);

}
