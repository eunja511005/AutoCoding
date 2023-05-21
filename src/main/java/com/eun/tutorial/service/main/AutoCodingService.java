package com.eun.tutorial.service.main;

import java.util.List;

import com.eun.tutorial.dto.main.AutocodingFieldDTO;

public interface AutoCodingService {
	List<AutocodingFieldDTO> getAutoCodingList();
	int saveAutoCoding(AutocodingFieldDTO autocodingFieldDTO);
	int deleteAutoCoding(String id);
	AutocodingFieldDTO getAutoCodingListById(String id);
}
