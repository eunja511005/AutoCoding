package com.eun.tutorial.service.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.eun.tutorial.dto.main.RealEstatePriceItem;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApiServiceTest {
	
	@Autowired
    private ApiService apiService;

//    @Test
//    void sendRequest_Post_Success() throws URISyntaxException {
//        // Prepare request data
//        String endpointUrl = "https://example.com/api";
//        URI uri = new URI(endpointUrl);
//        HttpMethod method = HttpMethod.POST;
//        Object requestBody = createRequestBody();
//        HttpHeaders headers = createHeaders();
//        MediaType requestMediaType = MediaType.TEXT_PLAIN;
//        MediaType responseMediaType = MediaType.APPLICATION_JSON;
//        Class<String> responseType = String.class;
//
//        // Send the request
//        ResponseEntity<String> response = apiService.sendRequest(uri, method, requestBody, headers,
//                requestMediaType, responseMediaType, responseType);
//
//        // Verify the response
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        // Assert other response properties as needed
//    }
    
    @Test
    void sendRequest_Get_PublicData_Success() throws URISyntaxException {
    	// Prepare request data
        String endpointUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade";
        String serviceKey = "G8dsIOIinqIgxBio8kv6qyrYJ9OsQr%2F5PhyVheRm9NbJfGNNYQSUSKYZH%2BRlgmYs01YxjwihIRUKKyQE1j%2FO%2Fw%3D%3D";
        String LAWD_CD = "11110";
        String DEAL_YMD = "201512";
        endpointUrl = endpointUrl + "?serviceKey=" + serviceKey + "&LAWD_CD=" + LAWD_CD + "&DEAL_YMD=" + DEAL_YMD;

        URI uri = new URI(endpointUrl);
        HttpMethod method = HttpMethod.GET;
        Object requestBody = null;
        HttpHeaders headers = new HttpHeaders();
        MediaType requestMediaType = MediaType.APPLICATION_JSON;
        MediaType responseMediaType = MediaType.APPLICATION_JSON;
        Class<RealEstatePriceItem> responseType = RealEstatePriceItem.class;
    	
    	// Send the request
    	ResponseEntity<RealEstatePriceItem> response = apiService.sendRequest(uri, method, requestBody, headers,
    			requestMediaType, responseMediaType, responseType);
    	
    	List<RealEstatePriceItem.ItemDTO> items = response.getBody().getResponse().getBody().getItems().getItem();
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(49, items.size());
    }
    
    private static Object createRequestBody() {
        // 예시: JSON 형식의 요청 본문 데이터 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("serviceKey", "G8dsIOIinqIgxBio8kv6qyrYJ9OsQr/5PhyVheRm9NbJfGNNYQSUSKYZH+RlgmYs01YxjwihIRUKKyQE1j/O/w==");
        requestBody.put("LAWD_CD", 11110);
        requestBody.put("DEAL_YMD", "201512");
        return requestBody;
    }

    private static HttpHeaders createHeaders() {
        // 예시: 헤더 데이터 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer your-token");
        headers.set("X-Custom-Header", "custom-value");
        return headers;
    }

}
