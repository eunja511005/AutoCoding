package com.eun.tutorial.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    
    private String ALGORITHM = "AES";

    public String encrypt(String input, String salt) {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher;
        byte[] encryptedBytes = null;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encryptedBytes = cipher.doFinal((input + salt).getBytes());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} 
        
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedInput, String salt) {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher;
        byte[] decryptedBytes = null;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
	        byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput);
	        decryptedBytes = cipher.doFinal(decodedBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} 
        
        return new String(decryptedBytes).replace(salt, "");
    }
}

