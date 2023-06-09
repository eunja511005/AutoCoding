package com.eun.tutorial.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.eun.tutorial.dto.UserInfoDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtTokenUtil {

    private static final String SECRET_KEY = "your-secret-key";
    private static final long EXPIRATION_TIME = 86400000; // 토큰 유효 시간: 24시간

    // 토큰에서 사용자 이름을 추출하는 메소드
    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 토큰에서 만료 날짜를 추출하는 메소드
    public static Date extractExpirationDate(String token) {
        return extractClaims(token).getExpiration();
    }

    // 토큰의 Claims(클레임)을 추출하는 메소드
    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // 토큰이 만료되었는지 확인하는 메소드
    private static Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    // 토큰을 생성하는 메소드
    public static String generateToken(UserInfoDTO userInfoDTO) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userInfoDTO.getUsername());
    }

    // 토큰을 생성하는 내부 메소드
    private static String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 토큰의 유효성을 검사하는 메소드
    public static boolean validateToken(String token, UserInfoDTO userInfoDTO) {
        String username = extractUsername(token);
        return (username.equals(userInfoDTO.getUsername()) && !isTokenExpired(token));
    }
}

