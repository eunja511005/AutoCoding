package com.eun.tutorial.service.main;

import java.util.List;

import com.eun.tutorial.dto.main.MenuDTO;

public interface MenuService {
	List<MenuDTO> getMenuList();
	String getMenuAuthByRole(String role);
	int saveMenu(MenuDTO menuDTO);
	int deleteMenu(String id);
	MenuDTO getMenuListById(String id);
	String generateMenuHtml();
	int updateMenu(MenuDTO menuDTO);
}