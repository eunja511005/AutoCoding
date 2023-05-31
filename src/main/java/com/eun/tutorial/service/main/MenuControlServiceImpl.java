/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the @autoCoding.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The @autoCoding shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

package com.eun.tutorial.service.main;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eun.tutorial.aspect.annotation.CheckAuthorization;
import com.eun.tutorial.aspect.annotation.CreatePermission;
import com.eun.tutorial.dto.main.MenuControlDTO;
import com.eun.tutorial.mapper.main.MenuControlMapper;

import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MenuControlServiceImpl implements MenuControlService {

	private final MenuControlMapper menuControlMapper;

	@Override
	public List<MenuControlDTO> getMenuControlList() {
		return menuControlMapper.selectMenuControlList();
	}

	@Override
	@CreatePermission
	public int saveMenuControl(MenuControlDTO menuControlDTO) {
		menuControlDTO.setId("menuControl_"+UUID.randomUUID());
		return menuControlMapper.insertMenuControl(menuControlDTO);
	}

	@Override
	@CheckAuthorization
	public int updateMenuControl(MenuControlDTO menuControlDTO) {
		return menuControlMapper.updateMenuControl(menuControlDTO);
	}

	@Override
	@CheckAuthorization
	public int deleteMenuControl(String id) {
		return menuControlMapper.deleteMenuControl(id);
	}

	@Override
	public MenuControlDTO getMenuControlListById(String id) {
		return menuControlMapper.getMenuControlListById(id);
	}

	@Override
	public List<MenuControlDTO> getMenuControlByRoleId(String roleId) {
		return menuControlMapper.getMenuControlByRoleId(roleId);
	}

}