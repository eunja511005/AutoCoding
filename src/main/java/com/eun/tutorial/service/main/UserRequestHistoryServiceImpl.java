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
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.aspect.annotation.SetCreateAndUpdateId;
import com.eun.tutorial.aspect.annotation.SetUserTimeZoneAndFormat;
import com.eun.tutorial.dto.main.UserRequestHistoryDTO;
import com.eun.tutorial.mapper.main.UserRequestHistoryMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserRequestHistoryServiceImpl implements UserRequestHistoryService {

	private final UserRequestHistoryMapper userRequestHistoryMapper;

	@Override
	@SetUserTimeZoneAndFormat
	public List<UserRequestHistoryDTO> getUserRequestHistoryList() {
		return userRequestHistoryMapper.selectUserRequestHistoryList();
	}

	@Override
	@SetCreateAndUpdateId
	public int saveUserRequestHistory(UserRequestHistoryDTO userRequestHistoryDTO) {
		userRequestHistoryDTO.setId("userRequestHistory_"+UUID.randomUUID());
		return userRequestHistoryMapper.insertUserRequestHistory(userRequestHistoryDTO);
	}

	@Override
	@SetCreateAndUpdateId
	public int updateUserRequestHistory(UserRequestHistoryDTO userRequestHistoryDTO) {
		return userRequestHistoryMapper.updateUserRequestHistory(userRequestHistoryDTO);
	}

	@Override
	public int deleteUserRequestHistory(String id) {
		return userRequestHistoryMapper.deleteUserRequestHistory(id);
	}

	@Override
	@Transactional
	public UserRequestHistoryDTO getUserRequestHistoryListById(String id) {
		return userRequestHistoryMapper.getUserRequestHistoryListById(id);
	}
	
	@Override
	public List<Map<String, Object>> getUserRequestHistoryCountByDate() {
		return userRequestHistoryMapper.getUserRequestHistoryCountByDate();
	}

	@Override
	public List<Map<String, Object>> getUserRequestHistoryCountByCount() {
		return userRequestHistoryMapper.getUserRequestHistoryCountByCount();
	}
	

}