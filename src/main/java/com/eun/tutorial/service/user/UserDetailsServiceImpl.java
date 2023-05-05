package com.eun.tutorial.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.mapper.TestMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TestMapper testDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, Object> map = new HashMap();
		map.put("username", username); // 가져온 데이터에 키와 벨류값을 지정
        UserInfoDTO optionalUser = testDao.getUser(map);
        
        if(optionalUser != null) {
        	return new UserDetailsImpl(optionalUser);
        }else{
        	return null;
        }
    }
}
