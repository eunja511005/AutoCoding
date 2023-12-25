package com.eun.tutorial.api.v1.client;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.eun.tutorial.api.v1.exception.ApiErrorCode;
import com.eun.tutorial.api.v1.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiClientWithRestTemplate {
	
	private static final String BASE_URL = "http://localhost:8080/api";

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
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "/stock/register/v1";

        // 파일 준비
        URL fileUrl1 = ApiClientWithRestTemplate.class.getResource("file1.png");
        File file1 = new File(fileUrl1.getPath());
        URL fileUrl2 = ApiClientWithRestTemplate.class.getResource("file2.jpg");
        File file2 = new File(fileUrl2.getPath());

        // 멀티파트 요청을 위한 HttpEntity 객체 생성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("apiRequest", new HttpEntity<>(json, createJsonHeaders()));
        body.add("file1", new FileSystemResource(file1));
        body.add("file2", new FileSystemResource(file2));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", token); // 토큰 추가

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // RestTemplate을 사용하여 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // 응답 출력
        log.info("Response: " + response.getBody());
    }
	
	private static boolean checkTokenValidity(String authToken) {
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", authToken);
	    HttpEntity<String> entity = new HttpEntity<>(headers);

	    try {
	        ResponseEntity<String> response = restTemplate.exchange(
	            BASE_URL + "/check_token", HttpMethod.GET, entity, String.class);

	        return Boolean.parseBoolean(response.getBody());
	    } catch (Exception e) {
	        return false;
	    }
	}

	private static String getToken(String userName, String password) throws CustomException {
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("username", userName);
	    jsonObject.put("password", password);

	    HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

	    try {
	        ResponseEntity<String> response = restTemplate.postForEntity(
	            BASE_URL + "/getToken", entity, String.class);

	        if (response.getStatusCode().is2xxSuccessful()) {
	            return response.getBody();
	        } else {
	            throw new CustomException(ApiErrorCode.INTERNAL_SERVER_ERROR.getCode(), "Failed to get token, server responded with status code: " + response.getStatusCode());
	        }
	    } catch (Exception e) {
	        throw new CustomException(ApiErrorCode.INTERNAL_SERVER_ERROR.getCode(), "Error while getting token: " + e.getMessage());
	    }
	}
	
	private static HttpHeaders createJsonHeaders() {
	    HttpHeaders jsonHeaders = new HttpHeaders();
	    jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
	    return jsonHeaders;
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
