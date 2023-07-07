package com.eun.tutorial.util;

public class StringUtils {
    public static boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }
    
    // 입력값의 첫번째 문자 대문자로 변환
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    
    // 입력값의 첫번째 문자 소문자로 변화
    public static String lowercaseFirstCharacter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
