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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eun.tutorial.aspect.annotation.CheckAuthorization;
import com.eun.tutorial.aspect.annotation.CreatePermission;
import com.eun.tutorial.aspect.annotation.SetCreateAndUpdateId;
import com.eun.tutorial.aspect.annotation.SetUserTimeZoneAndFormat;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO.PostSearch;
import com.eun.tutorial.dto.main.LayoutDTO;
import com.eun.tutorial.mapper.main.LayoutMapper;
import com.eun.tutorial.util.FileUtil;
import com.eun.tutorial.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LayoutServiceImpl implements LayoutService {

	private final LayoutMapper layoutMapper;
	private final FileUtil fileUtil;

	@Override
	@SetUserTimeZoneAndFormat
	public List<LayoutDTO> getLayoutList() {
		return layoutMapper.selectLayoutList();
	}

	@Override
	@Transactional
	@SetCreateAndUpdateId
	@CreatePermission
	public int saveLayout(LayoutDTO layoutDTO) {
		layoutDTO.setId("layout_"+UUID.randomUUID());
		return layoutMapper.insertLayout(layoutDTO);
	}

	@Override
	@Transactional
	@SetCreateAndUpdateId
	@CheckAuthorization
	public int updateLayout(LayoutDTO layoutDTO) {
		return layoutMapper.updateLayout(layoutDTO);
	}

	@Override
	@Transactional
	@CheckAuthorization
	public int deleteLayout(String id) {
		return layoutMapper.deleteLayout(id);
	}

	@Override
	@CreatePermission
	public LayoutDTO getLayoutListById(String id) {
		return layoutMapper.getLayoutListById(id);
	}

	@Override
	public List<LayoutDTO> searchLayouts(DataTableRequestDTO searchDTO) throws JsonProcessingException {
		Map<String, Object> params = new HashMap<>();
		params.put("offset", searchDTO.getStart());
		params.put("limit", searchDTO.getLength());
		params.put("orderColumnName", searchDTO.getOrderColumnName());
		params.put("orderDirection", searchDTO.getOrderDirection());
		
		converCheckBoxAndMultiSelecBoxForSearch(searchDTO, params);
		
		params.put("search", searchDTO.getSearch());
		
		return layoutMapper.selectLayouts(params);
	}

	@Override
	public int getTotalCount(DataTableRequestDTO searchDTO) throws JsonProcessingException {
		Map<String, Object> params = new HashMap<>();
		
		converCheckBoxAndMultiSelecBoxForSearch(searchDTO, params);
		
		params.put("search", searchDTO.getSearch());
		
		return layoutMapper.getTotalCount(params);
	}

	private void converCheckBoxAndMultiSelecBoxForSearch(DataTableRequestDTO searchDTO, Map<String, Object> params) throws JsonProcessingException {
		// 체크 박스 변환
		PostSearch chkPostSearch = searchDTO.getSearch().get("chk");
		ObjectMapper objectMapper = new ObjectMapper();
		String chk = objectMapper.readValue(chkPostSearch.getValue(), String.class);
		if(!StringUtils.isBlank(chk) && chk.equals("false")) {
			params.put("chk", "N");
		}else {
			params.put("chk", "Y");
		}
		
		// 멀티 셀렉트 박스 변환
		PostSearch multiboxPostSearch = searchDTO.getSearch().get("multibox");
		if (multiboxPostSearch != null && multiboxPostSearch.getValue() != null) {
		    List<String> multibox = objectMapper.readValue(multiboxPostSearch.getValue(), List.class);
		    params.put("multibox", multibox);
		}
	}

	@Override
	public Map<String, Object> saveFile(MultipartFile file) throws IOException {
		Map<String, Object> res = new HashMap<>();
		res.put("createdFilePath", fileUtil.saveImage(file));
		return res;
	}

}