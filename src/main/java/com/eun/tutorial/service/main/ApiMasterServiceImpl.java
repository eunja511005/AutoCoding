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
import com.eun.tutorial.dto.main.ApiMasterDTO;
import com.eun.tutorial.mapper.main.ApiMasterMapper;

import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApiMasterServiceImpl implements ApiMasterService {

	private final ApiMasterMapper apiMasterMapper;

	@Override
	@SetUserTimeZoneAndFormat
	public List<ApiMasterDTO> getApiMasterList() {
		return apiMasterMapper.selectApiMasterList();
	}

	@Override
	@Transactional
	@SetCreateAndUpdateId
	@CreatePermission
	public int saveApiMaster(ApiMasterDTO apiMasterDTO) {
		apiMasterDTO.setId("apiMaster_"+UUID.randomUUID());
		return apiMasterMapper.insertApiMaster(apiMasterDTO);
	}

	@Override
	@Transactional
	@SetCreateAndUpdateId
	@CheckAuthorization
	public int updateApiMaster(ApiMasterDTO apiMasterDTO) {
		return apiMasterMapper.updateApiMaster(apiMasterDTO);
	}

	@Override
	@Transactional
	@CheckAuthorization
	public int deleteApiMaster(String id) {
		return apiMasterMapper.deleteApiMaster(id);
	}

	@Override
	@CreatePermission
	public ApiMasterDTO getApiMasterListById(String id) {
		return apiMasterMapper.getApiMasterListById(id);
	}

	@Override
	public ApiMasterDTO getApiMasterByAPIAndSystemName(String apiName, String systemName) {
		return apiMasterMapper.getApiMasterByAPIAndSystemName(apiName, systemName);
	}

}