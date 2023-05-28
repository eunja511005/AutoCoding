package com.eun.tutorial.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.main.AccessControlDTO;

@Mapper
public interface AccessControlMapper {
	List<AccessControlDTO> selectAccessControlList();
	int insertAccessControl(AccessControlDTO accessControlDTO);
	int updateAccessControl(AccessControlDTO accessControlDTO);
	int deleteAccessControl(String id);
	AccessControlDTO getAccessControlListById(String id);
	int getAccessControlListByResource(String resourceId, String loginId);
	int deleteByResourceIdUserId(String resourceId, String loginId);
}