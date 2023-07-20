package com.eun.tutorial.util;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.SecretKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyGeneratorExample {
	
	private static final int KEY_LENGTH = 256;

    public static void main(String[] args) {
        try {
            SecretKey aesKey = KeyGenerator.generateAESKey(KEY_LENGTH);
            byte[] keyBytes = aesKey.getEncoded();
            String encodedKey = bytesToHex(keyBytes);
            log.info("Generated AES Key: " + encodedKey);
            
            
            // RSA 공개키와 개인키 생성
            KeyPair keyPair = KeyGenerator.generateRSAKeyPair(2048);
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            EncryptionUtils utils = new EncryptionUtils();
            String dataToEncrypt = "민감한 개인 정보를 암호화합니다.";
            String encryptedData = utils.encryptWithPublicKey(dataToEncrypt, publicKey);
            String decryptedData = utils.decryptWithPrivateKey(encryptedData, privateKey);
            log.info("Original data: " + dataToEncrypt);
            log.info("Encrypted data: " + encryptedData);
            log.info("Decrypted data: " + decryptedData);
            
            byte[] publicKeyBytes = publicKey.getEncoded();
            String publicKeyString = Base64.getEncoder().encodeToString(publicKeyBytes);
            log.info("publicKeyString: " + publicKeyString);
            
            byte[] privateKeyBytes = privateKey.getEncoded();
            String privateKeyString = Base64.getEncoder().encodeToString(privateKeyBytes);
            log.info("privateKeyString: " + privateKeyString);
            
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

