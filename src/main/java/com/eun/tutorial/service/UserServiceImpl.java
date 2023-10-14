package com.eun.tutorial.service;

import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.mapper.UserMapper;
import com.eun.tutorial.service.main.EmailService;
import com.eun.tutorial.util.EncryptionUtils;
import com.eun.tutorial.util.SaltGenerator;
import com.eun.tutorial.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserMapper userDao;
	private final EncryptionUtils encryptionUtils;
	private final BCryptPasswordEncoder passwordEncoder; // 시큐리티에서 빈(Bean) 생성할 예정
	private final EmailService emailService;

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

	@Override
	public int updateLastLoginDt(String username, String sessionID) {
		return userDao.updateLastLoginDt(username, sessionID);
	}

	@Override
	public ApiResponse<String> resetPassword(UserInfoDTO userInfoDTO) throws GeneralSecurityException {
		
		//민감 정보(패스워드) 복호화
    	String encryptedDataStr = userInfoDTO.getPassword();
    	
    	if(StringUtils.isBlank(userInfoDTO.getUsername())|| StringUtils.isBlank(encryptedDataStr)) {
    		return new ApiResponse<>(false, "You must enter your username and password.", null);
    	}
    	
		// 비밀키를 사용하여 암호화된 데이터를 복호화 (AES-CBC 알고리즘 사용)
    	String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDkcaTG8+DU1UDhFvnCzI+dCz7oCYwgZ4UsTh50saNa69ZlW5yTq0hkKYxpazoZaGj+9zJM7+ULhRpFSbd6AgTl24fK1Vk97XiSlJbJX2KWZQQy7/lKosWUKnEan+A56XX5oTr9+vWyPGgxQa9m6JB7bbZ6XU79JK6SEwhGcYMcFBMygrB3SKa/uR4zLEoIYp8wkP1URzgRtnx79q3dyE9mEqYlKN0AZBzewYCjfzi92kE11Ov9gebytD/zZ9QOjGi69LvGV6cN1FGAWt2bFYLYTbgvyf29LJTjONG+9GUn801bTzGkIJZ0STxFTiw0Jtb+/tDx2gMHhcAP3aLfmXhRAgMBAAECggEAT7Re+5OHtHqbYm2zwwXAbdjIoAkEvSGhU24GLkz0Y/q5GTA3l0ZBwcDFXtqssMS/LYZuJG3nCnfsTJSF0an2r4RDAsAhnPvH/8ycN7JyIWspZeRYpHPaX/HW+KUjhNEx+mEIxijTpNZyvAzg+BTYpFgJPPRlZOlxh0Hgt0NPrXGiv5AGsZKHeMNR6ZmgIGRT38BwQat4e3azvqNGV89axfuWOktJByyWYT4A9P/Euse3zK93p1Q2vyATwBgnX3CT64xdShaUXaxuBmezoWF3gEXNoh2rVkDsD2IKZG1AF+t6CLJomeZwEfJnZYPtX5z42EUJobTw9AZWCXsZ5g3wiQKBgQDniOOaifJ88H6finbhaVksC10aMTxa57DBkPhCC7GKeWt651cbQBu68zoACi4SSgaBT302k/pEw1BAqslmAJkjJrdW4SP6gYJUzNyIkENHw86t5b8+fQqqhHdgHsvx5Mdr9eVlvyoyX/gZKN/tHgoD/VZdEgSpEP57DEW/DiDcvwKBgQD8lSWnlk9pbmCNbU9DNIQsdHyi9k7Asb7NpPy1XS/uOLCdniFA7b3OvmgQDIMhc/qXkYhRAChhR7LIT6DgY566tOfT1tm5FZ1tRn7eg0vwrTZ+TLEko6i1L/Tm0QrSGVOrnDjm0+N+NKrG54zHOxOpO5XvpS8iG7S5KO3m5TMe7wKBgQC3wKBCCbD1DzivDYkDpEQs/GfLXb/0tWRGevNMF1Oz/mEajXdIHTzkxhwF5A5kXXOENL2/DvnUkN2kNObZmSfwCc3/mXagXSA+hMeRaky7K99fi7KXuU05vx+unUJmm5bZS7HfajPm/ts7vIDbArgYKnrcKmygcOhGZ5sC4geaqQKBgQDMKgrL0fXStQOajdbZ7eNAw8/TMeEqZQJj247hUrfhiTVJ0n3yq7kXGlWnU1XTfpn6Vgqn3sbFC116CNNzTVMKfBw/4ZUPxGcB40+9sMd7fadko700bo16F4+P2z0x4oL9XkOoYXGrnArGyHfEuv4Dd0SU9yKIIXkNigXy8yVFRQKBgE3ooHkxRnuyG8zzXOGNdhyRFL41NBMdSQaWMVhO8g8WBW3apVbyytknc0JAFUnxEsJjsgLIugHx+rtr1wmsYzHXpSH6coS3jFR/iAStkSrCcNuB+FQi70vkrtUcptmWkRXU29SACVZhApZskLBcFZUXDpTaAKYz+M60Y04v+0we";
        String decryptedData = encryptionUtils.decryptWithPrivateKey(encryptedDataStr, privateKey);

        log.info("decryptedData : {}", decryptedData); 
        
        Map<String, Object> map = new HashMap<>();
        map.put("username", userInfoDTO.getUsername()); // 가져온 데이터에 키와 값을 지정
        UserInfoDTO user = userDao.getUser(map);
        
		if (user == null) {
			return new ApiResponse<>(false, "User does not exist. Please check username again.", null);
		} else if (passwordEncoder.matches(decryptedData, user.getPassword())) {
			 // 유저 패스워드 업데이트
			userDao.updatePassword(userInfoDTO.getUsername(), passwordEncoder.encode(userInfoDTO.getNewPassword()));
			
			return new ApiResponse<>(true, "The password has been successfully changed.", null);
		} else {
			return new ApiResponse<>(false, "The current password is incorrect.", null);
		}
			
	}
	
    // 임시 비밀번호 생성 메서드
    private String generateTemporaryPassword() {
        // 임시 비밀번호 생성 로직을 여기에 추가하세요.
        // 예를 들어 랜덤 문자열 생성 또는 임의의 패스워드 생성 방법을 사용할 수 있습니다.
        // 임시 비밀번호는 보안을 고려하여 충분히 복잡하게 생성해야 합니다.

        // 여기서는 간단한 예시로 랜덤 8자리의 임시 비밀번호를 생성합니다.
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder temporaryPassword = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            temporaryPassword.append(characters.charAt(random.nextInt(characters.length())));
        }
        return temporaryPassword.toString();
    }

	@Override
	public ApiResponse<String> recoveryPassword(UserInfoDTO userInfoDTO) throws GeneralSecurityException {
		
		//민감 정보(이메일) 복호화
    	String encryptedDataStr = userInfoDTO.getEmail();
    	
    	if(StringUtils.isBlank(userInfoDTO.getUsername())|| StringUtils.isBlank(encryptedDataStr)) {
    		return new ApiResponse<>(false, "You must enter your username and email.", null);
    	}
    	
		// 비밀키를 사용하여 암호화된 데이터를 복호화 (AES-CBC 알고리즘 사용)
    	String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDkcaTG8+DU1UDhFvnCzI+dCz7oCYwgZ4UsTh50saNa69ZlW5yTq0hkKYxpazoZaGj+9zJM7+ULhRpFSbd6AgTl24fK1Vk97XiSlJbJX2KWZQQy7/lKosWUKnEan+A56XX5oTr9+vWyPGgxQa9m6JB7bbZ6XU79JK6SEwhGcYMcFBMygrB3SKa/uR4zLEoIYp8wkP1URzgRtnx79q3dyE9mEqYlKN0AZBzewYCjfzi92kE11Ov9gebytD/zZ9QOjGi69LvGV6cN1FGAWt2bFYLYTbgvyf29LJTjONG+9GUn801bTzGkIJZ0STxFTiw0Jtb+/tDx2gMHhcAP3aLfmXhRAgMBAAECggEAT7Re+5OHtHqbYm2zwwXAbdjIoAkEvSGhU24GLkz0Y/q5GTA3l0ZBwcDFXtqssMS/LYZuJG3nCnfsTJSF0an2r4RDAsAhnPvH/8ycN7JyIWspZeRYpHPaX/HW+KUjhNEx+mEIxijTpNZyvAzg+BTYpFgJPPRlZOlxh0Hgt0NPrXGiv5AGsZKHeMNR6ZmgIGRT38BwQat4e3azvqNGV89axfuWOktJByyWYT4A9P/Euse3zK93p1Q2vyATwBgnX3CT64xdShaUXaxuBmezoWF3gEXNoh2rVkDsD2IKZG1AF+t6CLJomeZwEfJnZYPtX5z42EUJobTw9AZWCXsZ5g3wiQKBgQDniOOaifJ88H6finbhaVksC10aMTxa57DBkPhCC7GKeWt651cbQBu68zoACi4SSgaBT302k/pEw1BAqslmAJkjJrdW4SP6gYJUzNyIkENHw86t5b8+fQqqhHdgHsvx5Mdr9eVlvyoyX/gZKN/tHgoD/VZdEgSpEP57DEW/DiDcvwKBgQD8lSWnlk9pbmCNbU9DNIQsdHyi9k7Asb7NpPy1XS/uOLCdniFA7b3OvmgQDIMhc/qXkYhRAChhR7LIT6DgY566tOfT1tm5FZ1tRn7eg0vwrTZ+TLEko6i1L/Tm0QrSGVOrnDjm0+N+NKrG54zHOxOpO5XvpS8iG7S5KO3m5TMe7wKBgQC3wKBCCbD1DzivDYkDpEQs/GfLXb/0tWRGevNMF1Oz/mEajXdIHTzkxhwF5A5kXXOENL2/DvnUkN2kNObZmSfwCc3/mXagXSA+hMeRaky7K99fi7KXuU05vx+unUJmm5bZS7HfajPm/ts7vIDbArgYKnrcKmygcOhGZ5sC4geaqQKBgQDMKgrL0fXStQOajdbZ7eNAw8/TMeEqZQJj247hUrfhiTVJ0n3yq7kXGlWnU1XTfpn6Vgqn3sbFC116CNNzTVMKfBw/4ZUPxGcB40+9sMd7fadko700bo16F4+P2z0x4oL9XkOoYXGrnArGyHfEuv4Dd0SU9yKIIXkNigXy8yVFRQKBgE3ooHkxRnuyG8zzXOGNdhyRFL41NBMdSQaWMVhO8g8WBW3apVbyytknc0JAFUnxEsJjsgLIugHx+rtr1wmsYzHXpSH6coS3jFR/iAStkSrCcNuB+FQi70vkrtUcptmWkRXU29SACVZhApZskLBcFZUXDpTaAKYz+M60Y04v+0we";
        String decryptedData = encryptionUtils.decryptWithPrivateKey(encryptedDataStr, privateKey);

        log.info("decryptedData : {}", decryptedData); 
        
        Map<String, Object> map = new HashMap<>();
        map.put("username", userInfoDTO.getUsername()); // 가져온 데이터에 키와 값을 지정
        UserInfoDTO user = userDao.getUser(map);
        
		if (user == null) {
			return new ApiResponse<>(false, "User does not exist. Please check username again.", null);
		} else {
			if (user.getEmail() != null && user.getSalt() != null) {
				String salt = Base64.getEncoder().encodeToString(user.getSalt());

				// 이메일 복호화
				String decryptedEmail = encryptionUtils.decrypt(user.getEmail(), salt);
				if (!decryptedEmail.equals(decryptedData)) {
					return new ApiResponse<>(false, "You have entered an incorrect email. Please enter the email address you entered when signing up.", null);
				}
			} else {
				return new ApiResponse<>(false,
						"Email information does not exist. Please proceed with membership registration.", null);
			}
		}
		
        // 임시 비밀번호 생성
        String temporaryPassword = generateTemporaryPassword();
        log.info("temporaryPassword : {}", temporaryPassword); 
		
        // 유저 패스워드 업데이트
		userDao.updatePassword(userInfoDTO.getUsername(), passwordEncoder.encode(temporaryPassword));
		
        // 이메일 본문에 임시 비밀번호 추가
        // 이메일 본문 작성
        String body = "<h2 style='color: #5e9ca0;'>Password Recovery</h2>" +
                "<p style='color: #000000;'>Dear User,</p>" +
                "<p style='color: #000000;'>Your temporary password is: <strong>" + temporaryPassword + "</strong></p>" +
                "<p style='color: #000000;'>Please use this password to login and reset your password.</p>" +
                "<p style='color: #000000;'>Best regards,</p>" +
                "<p style='color: #000000;'>Your Application Team</p>";
		
		return new ApiResponse<>(true, "Your temporary password has been sent successfully. Please check your email.", 
				emailService.sendSimpleEmail(decryptedData, "Password Recovery", body));
	}

}
