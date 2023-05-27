package com.eun.tutorial.service.main;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.main.AccessControlDTO;
import com.eun.tutorial.mapper.main.AccessControlMapper;

import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccessControlServiceImpl implements AccessControlService {

	private final AccessControlMapper accessControlMapper;

	@Override
	public List<AccessControlDTO> getAccessControlList() {
		return accessControlMapper.selectAccessControlList();
	}

	@Override
	public int saveAccessControl(AccessControlDTO accessControlDTO) {
		if (StringUtils.isBlank(accessControlDTO.getId())) {
			accessControlDTO.setId("accessControl_"+UUID.randomUUID());
			return accessControlMapper.insertAccessControl(accessControlDTO);
		} else {
			return accessControlMapper.updateAccessControl(accessControlDTO);
		}
	}

	@Override
	public int deleteAccessControl(String id) {
		return accessControlMapper.deleteAccessControl(id);
	}

	@Override
	public AccessControlDTO getAccessControlListById(String id) {
		return accessControlMapper.getAccessControlListById(id);
	}

}