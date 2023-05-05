package com.eun.tutorial.service;

import java.util.Collection;

import com.eun.tutorial.dto.Book;
import com.eun.tutorial.dto.UserInfoDTO;

public interface UserService {
//    Collection<UserInfoDTO> getUsers();
    UserInfoDTO addUser(UserInfoDTO userInfoDTO);
}
