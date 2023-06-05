/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the @autoCoding.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The @autoCoding shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

package com.eun.tutorial.service.main;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Base64;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eun.tutorial.aspect.annotation.CheckAuthorization;
import com.eun.tutorial.aspect.annotation.CreatePermission;
import com.eun.tutorial.dto.main.UserManageDTO;
import com.eun.tutorial.mapper.main.UserManageMapper;
import com.eun.tutorial.util.EncryptionUtils;
import com.eun.tutorial.util.SaltGenerator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserManageServiceImpl implements UserManageService {

	private final UserManageMapper userManageMapper;
	private final EncryptionUtils encryptionUtils;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<UserManageDTO> getUserManageList() {
	    List<UserManageDTO> userManageDTOList = userManageMapper.selectUserManageList();
	    
	    for (UserManageDTO userManageDTO : userManageDTOList) {
	        if (userManageDTO.getEmail() != null && userManageDTO.getSalt() != null) {
	            String salt = Base64.getEncoder().encodeToString(userManageDTO.getSalt());
	            
	            // 이메일 복호화
	            String decryptedEmail = encryptionUtils.decrypt(userManageDTO.getEmail(), salt);
	            userManageDTO.setEmail(decryptedEmail);
	            
	            // 마지막 로그인 시간을 사용자의 지역 시간으로 변환
	            if (userManageDTO.getLastLoginDt() != null && userManageDTO.getUserTimeZone() != null) {
	                // 사용자가 정의한 형식 또는 기본 형식을 가져옴
	                String formatterPattern = userManageDTO.getDateTimeFormatter() != null ?
	                        userManageDTO.getDateTimeFormatter() : "yyyy년 MM월 dd일 HH:mm";
	                
	                // 마지막 로그인 시간을 변환하기 위한 DateTimeFormatter 설정
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	                LocalDateTime lastLoginDateTime = LocalDateTime.parse(userManageDTO.getLastLoginDt(), formatter);
	                
	                // 시스템의 타임존 설정
	                ZoneId userZone = ZoneId.of(System.getProperty("user.timezone"));
	                
	                // 유저의 타임존 설정
	                ZoneId userTimeZone = ZoneId.of(userManageDTO.getUserTimeZone());
	                
	                // 최종 로그인 시간을 시스템의 타임존으로 변환한 ZonedDateTime
	                ZonedDateTime lastLoginZonedDateTime = lastLoginDateTime.atZone(userZone);
	                
	                // 최종 로그인 시간을 유저의 타임존으로 변환
	                ZonedDateTime convertedZonedDateTime = lastLoginZonedDateTime.withZoneSameInstant(userTimeZone);
	                
	                // 변환된 시간을 지정된 형식으로 포맷팅
	                String formattedDateTime = convertedZonedDateTime.format(DateTimeFormatter.ofPattern(formatterPattern));
	                
	                // 변환된 시간을 다시 userManageDTO에 설정
	                userManageDTO.setLastLoginDt(formattedDateTime);
	            }
	        }
	    }
	    
	    return userManageDTOList;
	}


	@Override
	@CreatePermission
	public int saveUserManage(UserManageDTO userManageDTO) {
		return userManageMapper.insertUserManage(userManageDTO);
	}

	@Override
	@CheckAuthorization
	public int updateUserManage(UserManageDTO userManageDTO) {
		return userManageMapper.updateUserManage(userManageDTO);
	}

	@Override
	@CheckAuthorization
	public int deleteUserManage(String username) {
		return userManageMapper.deleteUserManage(username);
	}

	@Override
	public UserManageDTO getUserManageListById(String username) {
		UserManageDTO userManageDTO = userManageMapper.getUserManageListById(username);
		
		if(userManageDTO.getEmail()!=null && userManageDTO.getSalt()!=null) {
			
			String salt = Base64.getEncoder().encodeToString(userManageDTO.getSalt());
			
            //이메일 복호화
            String decryptedEmail = encryptionUtils.decrypt(userManageDTO.getEmail(), salt);
			userManageDTO.setEmail(decryptedEmail);
		}
		
		return userManageDTO;
	}

	@Override
	public int mergeUser(UserManageDTO userManageDTO) {
		
		UserManageDTO searchedDTO = userManageMapper.getUserManageListById(userManageDTO.getUsername());
		if(searchedDTO==null) {
			//패스워드 암호화 저장
			userManageDTO.setPassword(passwordEncoder.encode(userManageDTO.getPassword()));
			
			// 이메일 암호화 저장
			byte[] generateSalt = SaltGenerator.generateRandomSalt();
			String salt = Base64.getEncoder().encodeToString(generateSalt);
	        String encryptedEmail = encryptionUtils.encrypt(userManageDTO.getEmail(), salt);
	        userManageDTO.setEmail(encryptedEmail);
	        userManageDTO.setSalt(generateSalt);
	        
	        return userManageMapper.insertUserManage(userManageDTO);
		}else {
			//패스워드 암호화 저장
			userManageDTO.setPassword(passwordEncoder.encode(userManageDTO.getPassword()));
			
			// 이메일 암호화 저장
			String salt = Base64.getEncoder().encodeToString(searchedDTO.getSalt());
	        String encryptedEmail = encryptionUtils.encrypt(userManageDTO.getEmail(), salt);
	        userManageDTO.setEmail(encryptedEmail);
			
			return userManageMapper.updateUserManage(userManageDTO);
		}
		
	}

}