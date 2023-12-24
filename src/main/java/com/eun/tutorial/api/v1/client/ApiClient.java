package com.eun.tutorial.api.v1.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.eun.tutorial.api.v1.dto.ApiResponse;
import com.eun.tutorial.api.v1.exception.ApiErrorCode;
import com.eun.tutorial.api.v1.exception.CustomException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiClient {
	
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        String json = createJsonRequestBody();
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRvQ29kaW5nMSIsImV4cCI6MTcwMzMwMDk0OCwiaWF0IjoxNzAzMjE0NTQ4fQ.RKISj8Bq2_1g2lQ6c917Jv-nYExT61eesCEYNVxqK2I";
        String userName = "autoCoding1";
		String password = "jw0713!@";
		
        try {
            if (!checkTokenValidity(token)) {
                log.info("Token is invalid. Fetching a new token...");
				token = "Bearer " + getToken(userName, password);
            }
            callApi(json, token);
        } catch (Exception e) {
            log.error("An error occurred: " + ExceptionUtils.getStackTrace(e));
        }
    }

    private static void callApi(String json, String token) {
        try {
            HttpResponse<String> response = sendPostRequest("/stock/search/v1", json, token);
            processApiResponse(response);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } catch (InterruptedException e) {
            log.error("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // 인터럽트 상태 재설정
        }
    }


    private static HttpResponse<String> sendPostRequest(String path, String json, String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .POST(BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static void processApiResponse(HttpResponse<String> response) {
        log.debug("Status Code: " + response.statusCode());

        if (response.statusCode() == HttpStatus.OK.value()) {
            ApiResponse<Object> apiResponse = parseApiResponse(response.body());
            if (apiResponse != null && apiResponse.isSuccess()) {
                log.debug("Response Data: " + apiResponse.getData());
            } else if (apiResponse != null) {
                log.debug("Result Code: " + apiResponse.getResultCode());
                log.debug("Result Message: " + apiResponse.getResultMessage());
            }
        } else {
            log.debug("Response Error: " + response.body());
        }
    }

    private static ApiResponse<Object> parseApiResponse(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<ApiResponse<Object>>() {});
        } catch (IOException e) {
            log.error("Failed to parse ApiResponse: " + e.getMessage());
            return null;
        }
    }
    
    private static boolean checkTokenValidity(String authToken) throws CustomException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/check_token"))
                .header("Authorization", authToken)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Boolean.parseBoolean(response.body());
        } catch (IOException | InterruptedException e) {
        	Thread.currentThread().interrupt(); // 인터럽트 상태 재설정
            // 네트워크 오류 또는 인터럽트 발생시 CustomException을 던짐
            throw new CustomException(ApiErrorCode.INTERNAL_SERVER_ERROR.getCode(), "Error while validating token: " + e.getMessage());
        }
    }


    private static String getToken(String userName, String password) throws CustomException {
        JSONObject json = new JSONObject();
        json.put("username", userName);
        json.put("password", password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getToken"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                return response.body();
            } else {
                // 서버 오류 응답에 대한 처리
                throw new CustomException(ApiErrorCode.INTERNAL_SERVER_ERROR.getCode(), "Failed to get token, server responded with status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
        	Thread.currentThread().interrupt(); // 인터럽트 상태 재설정
            // 네트워크 오류 또는 인터럽트 발생시 CustomException을 던짐
            throw new CustomException(ApiErrorCode.INTERNAL_SERVER_ERROR.getCode(), "Error while getting token: " + e.getMessage());
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
        		+ "      \"stockSearchLocation\": \"L\",\r\n"
        		+ "      \"stockSearchType\": \"M\"\r\n"
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

