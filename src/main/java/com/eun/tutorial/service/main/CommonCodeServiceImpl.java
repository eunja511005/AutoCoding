package com.eun.tutorial.service.main;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.main.CommonCodeDTO;
import com.eun.tutorial.mapper.main.CommonCodeMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {
	private final CommonCodeMapper commonCodeMapper;
	
	@Override
	public List<CommonCodeDTO> getCommonCodeList() {
		return commonCodeMapper.selectCommonCodeList();
	}

	@Override
	public int saveCommonCode(CommonCodeDTO commonCodeDTO) {
        if (commonCodeDTO.getId() == null) {
            return commonCodeMapper.insertCommonCode(commonCodeDTO);
        } else {
        	return commonCodeMapper.updateCommonCode(commonCodeDTO);
        }
	}

	@Override
	public int deleteCommonCode(Long id) {
		return commonCodeMapper.deleteCommonCode(id);
	}

	@Override
	public CommonCodeDTO getCommonCodeListById(String id) {
		return commonCodeMapper.getCommonCodeListById(id);
	}

}
