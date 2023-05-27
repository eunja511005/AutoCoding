package com.eun.tutorial.service.main;

import java.util.List;

import com.eun.tutorial.dto.main.AccessControlDTO;

public interface AccessControlService {
	List<AccessControlDTO> getAccessControlList();
	int saveAccessControl(AccessControlDTO accessControlDTO);
	int deleteAccessControl(String id);
	AccessControlDTO getAccessControlListById(String id);
}