package com.eun.tutorial.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.ImageRequestDTO;
import com.eun.tutorial.dto.ImageResponseDTO;
import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.mapper.ZthhFileAttachMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ZthhFileAttachServiceImpl implements ZthhFileAttachService {

	private final ZthhFileAttachMapper zthhFileAttachMapper;
	private static final Logger logger = LoggerFactory.getLogger(ZthhFileAttachServiceImpl.class);

    @Override
    public int save(ZthhFileAttachDTO zthhFileAttachDTO) {
    	return zthhFileAttachMapper.save(zthhFileAttachDTO);
    }

    @Override
    public List<ZthhFileAttachDTO> findAll() {
        return zthhFileAttachMapper.findAll();
    }

	@Override
	public ZthhFileAttachDTO getOne(Map<String, Object> map) {
		return zthhFileAttachMapper.getOne(map);
	}

	@Override
	public List<ImageResponseDTO> getFiles(ImageRequestDTO imageRequestDTO, String username) throws IOException {
		Map<String, Object> params = new HashMap<>();
		
        int offset = (imageRequestDTO.getPage() - 1) * imageRequestDTO.getSize();
        int limit = imageRequestDTO.getSize();
		
		params.put("offset", offset);
		params.put("limit", limit);
		params.put("creator", username);
		List<ZthhFileAttachDTO> zthhFileAttachDTOList = zthhFileAttachMapper.selectFiles(params);
		
		List<ImageResponseDTO> imageResponseDTOList = new ArrayList<>();
		ImageResponseDTO imageResponseDTO;
		for (ZthhFileAttachDTO zthhFileAttachDTO : zthhFileAttachDTOList) {
			imageResponseDTO = new ImageResponseDTO();
			imageResponseDTO.setFileName(zthhFileAttachDTO.getOriginalFileName());
			imageResponseDTO.setFileContent(readFile(zthhFileAttachDTO.getFilePath()));
			imageResponseDTO.setDescription("test");
			imageResponseDTO.setStatus("Processing");
			imageResponseDTO.setStartDate(LocalDate.of(2023, 8, 29));
			imageResponseDTO.setEndDate(LocalDate.of(2023, 9, 30));
			imageResponseDTO.setManager(username);
			
			imageResponseDTOList.add(imageResponseDTO);
		}
		
		return imageResponseDTOList;
	}
	
    private byte[] readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

	@Override
	public List<ZthhFileAttachDTO> getFileListByAttachId(String attachId) {
		return zthhFileAttachMapper.selectFileListByAttachId(attachId);
	}
}