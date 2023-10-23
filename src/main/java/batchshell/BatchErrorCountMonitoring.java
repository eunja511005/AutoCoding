package batchshell;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.eun.tutorial.dto.UserInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatchErrorCountMonitoring {
    private static String baseUrl = "http://192.168.219.105:8080/"; // 로컬
    private static final String WEB_SOCKET_URL = "ws://192.168.219.105:8080/ws-service"; // 로컬
    
    //private static String baseUrl = "http://193.123.233.105:8080/"; // 오라클
    //private static final String WEB_SOCKET_URL = "ws://193.123.233.105:8080/ws-service"; // 오라클 서버

	private static final String GET_TOKEN_URL = baseUrl+"api/getToken";

	public static void main(String[] args) throws IOException {
		log.info("Batch_Test Start.");

		Properties properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
		String dbUrl = properties.getProperty("spring.datasource.url");
		String dbUsername = properties.getProperty("spring.datasource.username");
		String dbPassword = properties.getProperty("spring.datasource.password");

		try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
				Statement statement = connection.createStatement()) {
			// 쿼리 실행
			String query = "SELECT COUNT(*) AS error_count FROM ZTHH_ERRORHIST WHERE CREATE_DT >= SYSDATE - (1/24)";
			try (ResultSet resultSet = statement.executeQuery(query)) {
				// 결과 처리
				while (resultSet.next()) {
					// 결과 처리
					int errorCount = resultSet.getInt("error_count");
					if (log.isDebugEnabled()) {
						log.debug("error_count : " + errorCount);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		try {
		    String jwtToken = getJWTToken(); // getJWTToken() 메서드가 올바르게 정의되어 있는지 확인하세요.
		    StompHeaders stompHeaders = new StompHeaders();
		    stompHeaders.add("Authorization", "Bearer " + jwtToken);

		    StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
		        @Override
		        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		            // 연결 후에 필요한 작업을 수행하세요.
		        	session.send("/app/chat/send", "test");
		        }
		        @Override
		        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
		        	exception.printStackTrace();
		        }

		        @Override
		        public void handleTransportError(StompSession session, Throwable exception) {
		        	exception.printStackTrace();
		        }
		    };
		    Object[] urlVariables = {};
		    WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
		    handshakeHeaders.add("Authorization", "Bearer " + jwtToken);
		    stompClient.connect(WEB_SOCKET_URL, handshakeHeaders, stompHeaders, sessionHandler, urlVariables);
		} catch (Exception e) {
		    e.printStackTrace();
		}

	}
	
    private static String getJWTToken() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("mobileUser");
        userInfoDTO.setPassword("jw0713!@");
    	
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userInfoDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GET_TOKEN_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(HttpResponse::body).get();
    }
    
}
