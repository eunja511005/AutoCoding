package com.eun.tutorial.service;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.mapper.UserMapper;
import com.eun.tutorial.util.EncryptionUtils;
import com.eun.tutorial.util.SaltGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
	private final UserMapper userDao;
	private final EncryptionUtils encryptionUtils;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired private BCryptPasswordEncoder passwordEncoder; // 시큐리티에서 빈(Bean) 생성할 예정

	@Override
	public UserInfoDTO addUser(UserInfoDTO userInfoDTO) {
		userInfoDTO.setCreateId(userInfoDTO.getUsername());
		userInfoDTO.setUpdateId(userInfoDTO.getUsername());
		userInfoDTO.setEnable(true);
		userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
		
		
		// 이메일 암호화 저장
		byte[] generateSalt = SaltGenerator.generateRandomSalt();
		String salt = Base64.getEncoder().encodeToString(generateSalt);
        String encryptedEmail = encryptionUtils.encrypt(userInfoDTO.getEmail(), salt);
        userInfoDTO.setEmail(encryptedEmail);
        userInfoDTO.setSalt(generateSalt);

		int result = userDao.addUser(userInfoDTO);
		return userInfoDTO;
	}

}
