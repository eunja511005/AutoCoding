package com.eun.tutorial.util;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {
	
    @Value("${myapp.encryption.key}")
    private String KEY;
    
    private String SYMMETRIC_ALGORITHM = "AES";//대칭키로 암호화(암호화 키와 복호화 키 동일)
    private String ASYMMETRIC_ALGORITHM = "RSA";//비대칭키로 암호화(암호화 키와 복호화 키 다름)
    

    // 개인 정보 DB 저장시 암호화
    public String encrypt(String input, String salt) {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), SYMMETRIC_ALGORITHM);
        Cipher cipher;
        byte[] encryptedBytes = null;
		try {
			cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encryptedBytes = cipher.doFinal((input + salt).getBytes());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} 
        
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 개인 정보 DB 저장시 복호화
    public String decrypt(String encryptedInput, String salt) {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), SYMMETRIC_ALGORITHM);
        Cipher cipher;
        byte[] decryptedBytes = null;
		try {
			cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
	        byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput);
	        decryptedBytes = cipher.doFinal(decodedBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} 
        
        return new String(decryptedBytes).replace(salt, "");
    }
    
    
    
    
    // 공개키를 사용하여 데이터 암호화
    public String encryptWithPublicKey(String input, PublicKey publicKey) {
        byte[] encryptedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance(ASYMMETRIC_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedBytes = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 개인키를 사용하여 데이터 복호화
    public String decryptWithPrivateKey(String encryptedInput, PrivateKey privateKey) {
        byte[] decryptedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance(ASYMMETRIC_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput);
            decryptedBytes = cipher.doFinal(decodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decryptedBytes);
    }
    
    // Base64 인코딩된 개인키 문자열을 PrivateKey 객체로 변환
    public PrivateKey privateKeyFromString(String privateKeyString) throws GeneralSecurityException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(privateKeySpec);
    }
    
    // 개인키를 사용하여 데이터 복호화
    public String decryptWithPrivateKey(String encryptedInput, String privateKeyString) throws GeneralSecurityException {
        PrivateKey privateKey = privateKeyFromString(privateKeyString);
        byte[] decryptedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance(ASYMMETRIC_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput);
            decryptedBytes = cipher.doFinal(decodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decryptedBytes);
    }
    
}

