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

package com.eun.tutorial.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.eun.tutorial.dto.main.ApiMasterDTO;

@Mapper
public interface ApiMasterMapper {
	List<ApiMasterDTO> selectApiMasterList();
	int insertApiMaster(ApiMasterDTO apiMasterDTO);
	int updateApiMaster(ApiMasterDTO apiMasterDTO);
	int deleteApiMaster(String id);
	ApiMasterDTO getApiMasterListById(String id);
	ApiMasterDTO getApiMasterByAPIAndSystemName(@Param("apiName") String apiName, @Param("systemName") String systemName);
}