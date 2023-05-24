package com.eun.tutorial.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.main.MenuDTO;

@Mapper
public interface MenuMapper {
	List<MenuDTO> selectMenuList();
	int insertMenu(MenuDTO menuDTO);
	int updateMenu(MenuDTO menuDTO);
	int deleteMenu(String id);
	MenuDTO getMenuListById(String id);
}