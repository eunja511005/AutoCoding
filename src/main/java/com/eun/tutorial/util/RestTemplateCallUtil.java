package com.eun.tutorial.util;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestTemplateCallUtil {
	
	@Autowired
	private RestTemplate customRestTemplate;

	public <T> ResponseEntity<T> sendRequest(URI uri, HttpMethod method, Object requestBody,
			HttpHeaders headers, MediaType requestMediaType, MediaType responseMediaType, Class<T> responseType) {
		
		// Set request headers
		headers.setContentType(requestMediaType);

		// Create HttpEntity with body and headers
		HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody, headers);

		// Set response headers
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(responseMediaType);

		// Send request
		ResponseEntity<T> response = customRestTemplate.exchange(uri, method, httpEntity, responseType);
		log.debug(response.toString());
		return ResponseEntity.status(response.getStatusCode()).headers(responseHeaders).body(response.getBody());
	}
}
