package com.eun.tutorial.service;

import java.security.GeneralSecurityException;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.dto.main.ApiResponse;

public interface UserService {
//    Collection<UserInfoDTO> getUsers();
    UserInfoDTO addUser(UserInfoDTO userInfoDTO);
    int updateLastLoginDt(String username, String sessionID);
	ApiResponse<String> resetPassword(UserInfoDTO userInfoDTO) throws GeneralSecurityException;
	ApiResponse<String> recoveryPassword(UserInfoDTO userInfoDTO) throws GeneralSecurityException;
}
