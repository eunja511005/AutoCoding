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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.eun.tutorial.dto.main.LayoutDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.eun.tutorial.dto.main.DataTableRequestDTO;

public interface LayoutService {
	List<LayoutDTO> getLayoutList();
	int saveLayout(LayoutDTO layoutDTO);
	int updateLayout(LayoutDTO layoutDTO);
	int deleteLayout(String id);
	LayoutDTO getLayoutListById(String id);
	List<LayoutDTO> searchLayouts(DataTableRequestDTO searchDTO) throws JsonProcessingException;
	int getTotalCount(DataTableRequestDTO searchDTO) throws JsonProcessingException;
	Map<String, Object> saveFile(MultipartFile file) throws IOException;
}