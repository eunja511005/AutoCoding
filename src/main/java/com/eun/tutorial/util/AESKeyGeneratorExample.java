package com.eun.tutorial.util;

import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AESKeyGeneratorExample {
	
	private static final int KEY_LENGTH = 256;

    public static void main(String[] args) {
        try {
            SecretKey aesKey = AESKeyGenerator.generateAESKey(KEY_LENGTH);
            byte[] keyBytes = aesKey.getEncoded();
            String encodedKey = bytesToHex(keyBytes);
            log.info("Generated AES Key: " + encodedKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
        	String hex = String.format("%02X", b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

