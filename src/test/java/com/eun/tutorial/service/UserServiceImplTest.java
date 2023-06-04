package com.eun.tutorial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.mapper.UserMapper;
import com.eun.tutorial.util.EncryptionUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceImplTest {
	@Autowired
	private UserMapper userDao;
	
	@Autowired
	private EncryptionUtils encryptionUtils;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @Test
    void testUpdateLastLoginDt() {

        // Create an instance of UserServiceImpl with mocked dependency
        UserServiceImpl userService = new UserServiceImpl(userDao, encryptionUtils, passwordEncoder);

        // Define the test username
        String username = "autoCoding";

        // Call the method to be tested
        int result = userService.updateLastLoginDt(username);
        
        // Retrieve the updated last login time from the database
		Map<String, Object> map = new HashMap<>();
		map.put("username", username); // 가져온 데이터에 키와 벨류값을 지정
        UserInfoDTO userInfoDTO = userDao.getUser(map);
        String lastLoginTime = userInfoDTO.getLastLoginDt();

        // Verify the method call and assertion
        assertNotNull(lastLoginTime);
        assertEquals(1, result);
    }
}
