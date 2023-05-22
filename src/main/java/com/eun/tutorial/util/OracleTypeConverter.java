package com.eun.tutorial.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OracleTypeConverter {
    public static Class<?> convertToJavaType(String oracleType) {
        if (oracleType.toUpperCase().startsWith("VARCHAR2") ||
        	oracleType.toUpperCase().startsWith("CHAR") ||
            oracleType.equalsIgnoreCase("CLOB")) {
            return String.class;
        } else if (oracleType.equalsIgnoreCase("NUMBER")) {
            return BigDecimal.class; // 또는 int, long, double 등 숫자 타입 중 하나로 매핑 가능
        } else if (oracleType.equalsIgnoreCase("DATE") ||
                   oracleType.equalsIgnoreCase("TIMESTAMP")) {
            return Timestamp.class; // 또는 java.util.Date, java.time.LocalDateTime 등으로 매핑 가능
        } else if (oracleType.equalsIgnoreCase("BLOB") ||
                   oracleType.equalsIgnoreCase("RAW")) {
            return byte[].class;
        }
        
        // 매핑되는 자바 타입이 없는 경우 예외 처리
        throw new IllegalArgumentException("Unsupported Oracle type: " + oracleType);
    }
}