package com.eun.tutorial.util;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESKeyGenerator {
    public static SecretKey generateAESKey(int keyLength) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[keyLength / 32];
        secureRandom.nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, "AES");
    }
}

