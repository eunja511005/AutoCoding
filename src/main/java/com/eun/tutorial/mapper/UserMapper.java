package com.eun.tutorial.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.UserInfoDTO;

@Mapper
public interface UserMapper {
    UserInfoDTO getUser(Map<String, Object> map);
    int addUser(UserInfoDTO userInfoDTO);
}