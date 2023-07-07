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

package com.eun.tutorial.service.external.pubdata;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.aspect.annotation.SetCreateAndUpdateId;
import com.eun.tutorial.aspect.annotation.SetUserTimeZoneAndFormat;
import com.eun.tutorial.dto.external.pubdata.ApiHistoryDTO;
import com.eun.tutorial.mapper.external.pubdata.ApiHistoryMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ApiHistoryServiceImpl implements ApiHistoryService {

	private final ApiHistoryMapper apiHistoryMapper;

	@Override
	@SetUserTimeZoneAndFormat
	public List<ApiHistoryDTO> getApiHistoryList() {
		return apiHistoryMapper.selectApiHistoryList();
	}

	@Override
	@SetCreateAndUpdateId
	public int saveApiHistory(ApiHistoryDTO apiHistoryDTO) {
    	try {//오류 발생해도 기존 로직에는 영향을 주지 않고 오류 로그만 남김
    		apiHistoryDTO.setId("apiHistory_"+UUID.randomUUID());
    		return apiHistoryMapper.insertApiHistory(apiHistoryDTO);
    	}catch (Exception e) {
    		log.error(e.getMessage());
		}
    	return 0;
	}

	@Override
	@SetCreateAndUpdateId
	public int updateApiHistory(ApiHistoryDTO apiHistoryDTO) {
		return apiHistoryMapper.updateApiHistory(apiHistoryDTO);
	}

	@Override
	public int deleteApiHistory(String id) {
		return apiHistoryMapper.deleteApiHistory(id);
	}

	@Override
	public ApiHistoryDTO getApiHistoryListById(String id) {
		return apiHistoryMapper.getApiHistoryListById(id);
	}
	
	@Override
	public List<Map<String, Object>> getApiHistoryCountByDate() {
		return apiHistoryMapper.getApiHistoryCountByDate();
	}

	@Override
	public List<Map<String, Object>> getApiHistoryCountByCount() {
		return apiHistoryMapper.getApiHistoryCountByCount();
	}
	

}