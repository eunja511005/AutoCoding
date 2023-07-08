package com.eun.tutorial.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.eun.tutorial.dto.external.pubdata.ApiHistoryDTO;
import com.eun.tutorial.dto.external.pubdata.RequestDetails;
import com.eun.tutorial.service.external.pubdata.ApiHistoryService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestTemplateCallUtil {
	
	@Autowired
	private RestTemplate customRestTemplate;
	
	@Autowired
	private ApiHistoryService apiHistoryService;
	
	@Autowired
	private MessageUtil messageUtil;

	public <I, O> ResponseEntity<O> sendRequest(RequestDetails<I, O> requestDetails) throws RestClientException, URISyntaxException {
		
	    String endpointUrl = requestDetails.getEndpointUrl();
	    String systemName = requestDetails.getSystemName();
	    String apiName = requestDetails.getApiName();
	    String httpMethod = requestDetails.getHttpMethod();
	    I requestBody = requestDetails.getRequestBody();
	    HttpHeaders headers = requestDetails.getHeaders();
	    MediaType requestMediaType = requestDetails.getRequestMediaType();
	    MediaType responseMediaType = requestDetails.getResponseMediaType();
	    Class<O> responseType = requestDetails.getResponseType();
	    String logYn = requestDetails.getLogYn();
		
        boolean loggingYn = false;
        if("Y".equals(logYn)) {
        	loggingYn = true;
        }
		
		// set Method
        HttpMethod method;
        if(httpMethod.equals("GET")) {
        	method = HttpMethod.GET;
        }else if(httpMethod.equals("POST")) {
        	method = HttpMethod.POST;
        }else {
        	String unknownHttpMethodMsg = messageUtil.getMessage("api.master.unknown.http.method");
            throw new IllegalArgumentException(unknownHttpMethodMsg);
        }
		
		// Set request headers
        if(headers==null) {
        	headers = new HttpHeaders();        
        }
		headers.setContentType(requestMediaType);

		// Create HttpEntity with body and headers
		HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody, headers);

		// Set response headers
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(responseMediaType);

		// Send request
		ResponseEntity<O> response = customRestTemplate.exchange(new URI(endpointUrl), method, httpEntity, responseType);
		log.debug(response.toString());
		
		if(loggingYn) {
			saveApiLog(endpointUrl, systemName, apiName, httpMethod, requestBody, headers, response);
		}
		
		return ResponseEntity.status(response.getStatusCode()).headers(responseHeaders).body(response.getBody());
	}

	private <I, O> void saveApiLog(String endpointUrl, String systemName, String apiName, String httpMethod,
			I requestBody, HttpHeaders headers, ResponseEntity<O> response) {
		ApiHistoryDTO apiHistoryDTO = new ApiHistoryDTO();
		apiHistoryDTO.setSystemName(systemName);
		apiHistoryDTO.setApiName(apiName);
		apiHistoryDTO.setHttpMethod(httpMethod);

		//request
		StringBuilder sb = new StringBuilder();
		sb.append(headers.toString())
		.append("\n")
		.append(endpointUrl)
		.append("\n");
		
		if(requestBody!=null) {
			sb.append(requestBody.toString());
		}
		
		String requestData = sb.toString();
		if (requestData.length() > 1000) {
		    requestData = requestData.substring(0, 1000);
		}
		apiHistoryDTO.setReqData(requestData);

		//response
		String responseData = response.toString();
		if (responseData.length() > 1000) {
		    responseData = responseData.substring(0, 1000);
		}
		apiHistoryDTO.setResData(responseData);
		
		apiHistoryService.saveApiHistory(apiHistoryDTO);
	}
}
