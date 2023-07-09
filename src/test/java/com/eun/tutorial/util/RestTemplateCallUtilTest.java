package com.eun.tutorial.util;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.eun.tutorial.dto.external.pubdata.GetUltraSrtNcstRootDTO;
import com.eun.tutorial.dto.external.pubdata.RequestDetails;
import com.eun.tutorial.dto.main.ApiMasterDTO;
import com.eun.tutorial.service.main.ApiMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class RestTemplateCallUtilTest {

	@Autowired
	RestTemplateCallUtil restTemplateCallUtil;
	
	@Autowired
	ApiMasterService apiMasterService;
	
	@Test
	void GetUltraSrtNcstRootDTO() throws RestClientException, URISyntaxException, JsonMappingException, JsonProcessingException {
		
		// 1. Given
		String apiName = "getUltraSrtNcst";
		String systemName = "PublicPortal";
		ApiMasterDTO apiMasterDTO = apiMasterService.getApiMasterByAPIAndSystemName(apiName, systemName);
		
		String sixAgo = DateUtils.getSomeHourAgo(1);
		String date = sixAgo.substring(0, 8);
		String hour = sixAgo.substring(8, 10)+"00";
		StringBuilder sb = new StringBuilder();
		sb.append(apiMasterDTO.getCallUrl());
		sb.append("?serviceKey="+apiMasterDTO.getAuthor());
		sb.append("&pageNo=1");
		sb.append("&numOfRows=1000");
		sb.append("&dataType=json");
		sb.append("&base_date="+date);
		sb.append("&base_time="+hour);
		sb.append("&nx=60");
		sb.append("&ny=121");
		
		HttpHeaders headers = new HttpHeaders();
        RequestDetails<Object, String> requestDetails = new RequestDetails<>();
        requestDetails.setEndpointUrl(sb.toString());
        requestDetails.setHttpMethod(apiMasterDTO.getHttpMethod());
        requestDetails.setRequestBody(null);
        requestDetails.setHeaders(headers);
        requestDetails.setRequestMediaType(MediaType.APPLICATION_JSON);
        requestDetails.setResponseMediaType(MediaType.APPLICATION_JSON);
        requestDetails.setResponseType(String.class);
        requestDetails.setLogYn(apiMasterDTO.getLogYn());
		
		// 2. When
		ResponseEntity<String> response = restTemplateCallUtil.sendRequest(requestDetails);
		
		String jsonString = response.getBody();
		
		log.info(" ### response {}", sb.toString());
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);		
        // JSON 문자열을 Map으로 변환
		GetUltraSrtNcstRootDTO getUltraSrtNcstRootDTO = objectMapper.readValue(jsonString, new TypeReference<GetUltraSrtNcstRootDTO>() {});
		
		// 3. Then
		assertEquals("00", getUltraSrtNcstRootDTO.getResponse().getHeader().getResultCode());
	}
}
