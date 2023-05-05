package com.eun.tutorial.service;

import java.util.List;
import java.util.Map;

import com.eun.tutorial.dto.ZthhFileAttachDTO;

public interface ZthhFileAttachService {
	int save(ZthhFileAttachDTO zthmError);
	List<ZthhFileAttachDTO> findAll();
	ZthhFileAttachDTO getOne(Map<String, Object> map);
}