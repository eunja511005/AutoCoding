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
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.aspect.annotation.CheckAuthorization;
import com.eun.tutorial.aspect.annotation.CreatePermission;
import com.eun.tutorial.aspect.annotation.SetUserTimeZoneAndFormat;
import com.eun.tutorial.aspect.annotation.SetCreateAndUpdateId;
import com.eun.tutorial.dto.main.SystemMasterDTO;
import com.eun.tutorial.mapper.main.SystemMasterMapper;

import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SystemMasterServiceImpl implements SystemMasterService {

	private final SystemMasterMapper systemMasterMapper;

	@Override
	@SetUserTimeZoneAndFormat
	public List<SystemMasterDTO> getSystemMasterList() {
		return systemMasterMapper.selectSystemMasterList();
	}

	@Override
	@Transactional
	@SetCreateAndUpdateId
	@CreatePermission
	public int saveSystemMaster(SystemMasterDTO systemMasterDTO) {
		systemMasterDTO.setId("systemMaster_"+UUID.randomUUID());
		return systemMasterMapper.insertSystemMaster(systemMasterDTO);
	}

	@Override
	@Transactional
	@SetCreateAndUpdateId
	@CheckAuthorization
	public int updateSystemMaster(SystemMasterDTO systemMasterDTO) {
		return systemMasterMapper.updateSystemMaster(systemMasterDTO);
	}

	@Override
	@Transactional
	@CheckAuthorization
	public int deleteSystemMaster(String id) {
		return systemMasterMapper.deleteSystemMaster(id);
	}

	@Override
	@CreatePermission
	public SystemMasterDTO getSystemMasterListById(String id) {
		return systemMasterMapper.getSystemMasterListById(id);
	}

}