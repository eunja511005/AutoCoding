package com.eun.tutorial.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.mapper.UserMapper;
import com.eun.tutorial.util.EncryptionUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserMapper userDao;
	private final EncryptionUtils encryptionUtils;

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, Object> map = new HashMap<>();
		map.put("username", username); // 가져온 데이터에 키와 벨류값을 지정
        UserInfoDTO userInfoDTO = userDao.getUser(map);
        
        if(userInfoDTO != null) {
            //이메일 복호화
            String decryptedEmail = encryptionUtils.decrypt(userInfoDTO.getEmail(), userInfoDTO.getSalt());
            userInfoDTO.setEmail(decryptedEmail);
        	
        	return new PrincipalDetails(userInfoDTO);
        }else{
        	return null;
        }
    }
}
