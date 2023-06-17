package com.eun.tutorial.mapper.main;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.eun.tutorial.dto.main.MenuDTO;

@Mapper
public interface MenuMapper {
	List<MenuDTO> selectMenuList();
	List<MenuDTO> getMenuAuthByRole(String role);
	int insertMenu(MenuDTO menuDTO);
	int updateMenu(MenuDTO menuDTO);
	int deleteMenu(String id);
	MenuDTO getMenuListById(String id);
	int updateMenuAuthByMenuIds(@Param("role") String role, @Param("menuIds") List<String> menuIds);
	int updateMenuControl();
}