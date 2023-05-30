package com.eun.tutorial.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltGenerator {

    public static byte[] generateRandomSalt() {
        byte[] salt = new byte[16]; // 16바이트(128비트)의 무작위 솔트 생성
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }
}


