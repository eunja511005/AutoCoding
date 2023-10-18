package com.eun.tutorial.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.eun.tutorial.dto.ImageRequestDTO;
import com.eun.tutorial.dto.ImageResponseDTO;
import com.eun.tutorial.dto.ZthhFileAttachDTO;

public interface ZthhFileAttachService {
	int save(ZthhFileAttachDTO zthmError);
	List<ZthhFileAttachDTO> findAll();
	List<ZthhFileAttachDTO> getFileListByAttachId(String attachId);
	ZthhFileAttachDTO getOne(Map<String, Object> map);
	List<ImageResponseDTO> getFiles(ImageRequestDTO imageRequestDTO, String username) throws IOException;
}