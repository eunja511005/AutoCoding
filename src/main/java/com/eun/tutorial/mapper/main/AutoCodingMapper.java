package com.eun.tutorial.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.main.AutocodingFieldDTO;

@Mapper
public interface  AutoCodingMapper {
	List<AutocodingFieldDTO> selectAutoCoding();
	int insertAutoCoding(AutocodingFieldDTO autocodingFieldDTO);
	int updateAutoCoding(AutocodingFieldDTO autocodingFieldDTO);
	int deleteAutoCoding(String id);
	AutocodingFieldDTO getAutoCodingListById(String id);
}
