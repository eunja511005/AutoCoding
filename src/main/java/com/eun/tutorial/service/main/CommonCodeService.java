package com.eun.tutorial.service.main;

import java.util.List;

import com.eun.tutorial.dto.main.CommonCodeDTO;

public interface CommonCodeService {

	List<CommonCodeDTO> getCommonCodeList();
	int saveCommonCode(CommonCodeDTO commonCodeDTO);
	int deleteCommonCode(Long id);
	CommonCodeDTO getCommonCodeListById(String id);
	List<CommonCodeDTO> getCommonCodesByCategory(String codeGroup);

}
