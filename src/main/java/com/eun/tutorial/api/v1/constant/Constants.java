package com.eun.tutorial.api.v1.constant;

public final class Constants {

    // 애플리케이션 설정
    public static final String APPLICATION_NAME = "MyApp";
    public static final String APPLICATION_VERSION = "1.0.0";

    // 데이터베이스 관련
    public static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    public static final String DB_USER = "dbuser";
    public static final String DB_PASSWORD = "dbpassword";

    // 네트워크 관련
    public static final int MAX_CONNECTIONS = 100;
    public static final int SOCKET_TIMEOUT_MS = 30000;

    // 파일 경로
    public static final String LOG_FILE_PATH = "/var/log/myapp.log";
    public static final String CONFIG_FILE_PATH = "/etc/myapp/config.xml";

    // 사용자 인터페이스
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String DEFAULT_FONT = "Arial";

    // 보안 관련
    public static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final int PASSWORD_HASH_ITERATIONS = 1000;
    public static final int PASSWORD_HASH_KEY_LENGTH = 256;

    // 오류 코드
    public static final int ERROR_CODE_NOT_FOUND = 404;
    public static final int ERROR_CODE_SERVER_ERROR = 500;

    // 기타 상수
    public static final double PI = 3.14159;
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_ZONE = "GMT+0";
    
    private Constants() {
        // 상수 클래스 인스턴스화 방지
    }
}
