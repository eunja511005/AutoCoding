package com.eun.tutorial.api.v1.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import com.eun.tutorial.api.v1.dto.ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiClient {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        String json = createJsonRequestBody();
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRvQ29kaW5nMSIsImV4cCI6MTcwMzMwMDk0OCwiaWF0IjoxNzAzMjE0NTQ4fQ.RKISj8Bq2_1g2lQ6c917Jv-nYExT61eesCEYNVxqK2I";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/stock/search/v1")) // API 엔드포인트 URL
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .POST(BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("Status Code: " + response.statusCode());

            if (response.statusCode() == HttpStatus.OK.value()) {
            	
            	ApiResponse<Object> apiResponse = parseApiResponse(response.body());
            	
                if (apiResponse != null) {
                	if(apiResponse.isSuccess()) {
                		log.debug("Response Data: " + apiResponse.getData());
                		log.debug("Response Error: " + response.body());
                	}else {
                		log.debug("Result Code: " + apiResponse.getResultCode());
                		log.debug("Result Messgae: " + apiResponse.getResultMessage());
                	}
                } else {
                	log.debug("Failed to parse ApiResponse or ApiResponse is null");
                }
                
            } else {
                // 오류 또는 다른 상태 코드 처리
            	log.debug("Response Error: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
			log.error(ExceptionUtils.getStackTrace(e));
			
			//스레드의 인터럽트 상태를 복원함으로써, 
			//같은 스레드 내에서 실행되는 다른 블로킹 작업이나 인터럽트를 확인하는 로직이 인터럽트 상태를 정확하게 감지할 수 있도록
			//현재 진행 중인 프로세스나 스레드의 실행을 일시 중단하고, 운영 체제나 다른 프로세스가 중요한 작업을 수행할 수 있도록 우선 순위를 조정
			Thread.currentThread().interrupt();
		} 
    }

    private static ApiResponse<Object> parseApiResponse(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<ApiResponse<Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static String createJsonRequestBody() {
        return "{\r\n"
        		+ "  \"commonHeader\": {\r\n"
        		+ "    \"clientId\": \"yourClientId\",\r\n"
        		+ "    \"requestId\": \"yourRequestId\",\r\n"
        		+ "    \"language\": \"en\",\r\n"
        		+ "    \"timeZone\": \"GMT+9\",\r\n"
        		+ "    \"timestamp\": \"20231222090912\"\r\n"
        		+ "  },\r\n"
        		+ "  \"data\": {\r\n"
        		+ "    \"stockHeader\": {\r\n"
        		+ "      \"mode\": \"L\"\r\n"
        		+ "    },\r\n"
        		+ "    \"stockItems\": [\r\n"
        		+ "      {\r\n"
        		+ "        \"itemCategory\": \"모바일\",\r\n"
        		+ "        \"itemNumber\": 123,\r\n"
        		+ "        \"modelCode\": \"MDL-1234\",\r\n"
        		+ "        \"plant\": \"서울공장\",\r\n"
        		+ "        \"storageLocation\": \"서울 창고\",\r\n"
        		+ "        \"valuationType\": \"가치 평가 유형1\",\r\n"
        		+ "        \"vendorCode\": \"VNDR-001\",\r\n"
        		+ "        \"requestQuantity\": 10,\r\n"
        		+ "        \"requestDate\": \"20231222090000\",\r\n"
        		+ "        \"confirmQuantity\": 8,\r\n"
        		+ "        \"confirmDate\": \"20231222120000\"\r\n"
        		+ "      },\r\n"
        		+ "      {\r\n"
        		+ "        \"itemCategory\": \"가전\",\r\n"
        		+ "        \"itemNumber\": 456,\r\n"
        		+ "        \"modelCode\": \"MDL-5678\",\r\n"
        		+ "        \"plant\": \"부산공장\",\r\n"
        		+ "        \"storageLocation\": \"부산 창고\",\r\n"
        		+ "        \"valuationType\": \"가치 평가 유형2\",\r\n"
        		+ "        \"vendorCode\": \"VNDR-002\",\r\n"
        		+ "        \"requestQuantity\": 5,\r\n"
        		+ "        \"requestDate\": \"20231223090000\",\r\n"
        		+ "        \"confirmQuantity\": 5,\r\n"
        		+ "        \"confirmDate\": \"20231223120000\"\r\n"
        		+ "      }\r\n"
        		+ "    ]\r\n"
        		+ "  }\r\n"
        		+ "}\r\n"
        		+ "";
    }

}

