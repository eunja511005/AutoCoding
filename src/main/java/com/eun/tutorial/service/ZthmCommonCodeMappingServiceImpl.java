package com.eun.tutorial.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.ZthmCommonCodeMappingDTO;
import com.eun.tutorial.mapper.ZthmCommonCodeMappingMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ZthmCommonCodeMappingServiceImpl implements ZthmCommonCodeMappingService {

	private final ZthmCommonCodeMappingMapper zthmCommonCodeMappingMapper;
	private static final Logger logger = LoggerFactory.getLogger(ZthmCommonCodeMappingServiceImpl.class);
	
	@Override
	public int save(ZthmCommonCodeMappingDTO zthmCommonCodeDTO) {
		return zthmCommonCodeMappingMapper.save(zthmCommonCodeDTO);
	}
	@Override
	public List<ZthmCommonCodeMappingDTO> findAll() {
		return zthmCommonCodeMappingMapper.findAll();
	}
	@Override
	public int delete(long id) {
		return zthmCommonCodeMappingMapper.delete(id);
	}
	@Override
	public List<ZthmCommonCodeMappingDTO> findByCodeMappingName(String codemappingName) {
		return zthmCommonCodeMappingMapper.findByCodeMappingName(codemappingName);
	}

}