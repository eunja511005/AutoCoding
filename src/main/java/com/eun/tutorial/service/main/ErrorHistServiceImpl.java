/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the developer.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The developer shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

package com.eun.tutorial.service.main;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eun.tutorial.aspect.annotation.CheckAuthorization;
import com.eun.tutorial.aspect.annotation.CreatePermission;
import com.eun.tutorial.dto.main.ErrorHistDTO;
import com.eun.tutorial.mapper.main.ErrorHistMapper;

import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ErrorHistServiceImpl implements ErrorHistService {

	private final ErrorHistMapper errorHistMapper;

	@Override
	public List<ErrorHistDTO> getErrorHistList() {
		return errorHistMapper.selectErrorHistList();
	}

	@Override
	//@CreatePermission
	public int saveErrorHist(ErrorHistDTO errorHistDTO) {
		errorHistDTO.setId("errorHist_"+UUID.randomUUID());
		return errorHistMapper.insertErrorHist(errorHistDTO);
	}

	@Override
	@CheckAuthorization
	public int updateErrorHist(ErrorHistDTO errorHistDTO) {
		return errorHistMapper.updateErrorHist(errorHistDTO);
	}

	@Override
	@CheckAuthorization
	public int deleteErrorHist(String id) {
		return errorHistMapper.deleteErrorHist(id);
	}

	@Override
	public ErrorHistDTO getErrorHistListById(String id) {
		return errorHistMapper.getErrorHistListById(id);
	}

	@Override
	public List<Map<String, Object>> getErrorData() {
		return errorHistMapper.getErrorCountByDate();
	}

}