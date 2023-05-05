package com.eun.tutorial.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.ZthhErrorDTO;

@Mapper
public interface CommonMapper {
	int save(ZthhErrorDTO zthmError);
	List<ZthhErrorDTO> findAll();
	int delete(int deleteDay);
}
