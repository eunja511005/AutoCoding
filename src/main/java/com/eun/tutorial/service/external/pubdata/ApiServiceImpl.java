package com.eun.tutorial.service.external.pubdata;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService{

	private final RestTemplate restTemplate;

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
		ResponseEntity<T> response = restTemplate.exchange(uri, method, httpEntity, responseType);
		log.debug(response.toString());
		return ResponseEntity.status(response.getStatusCode()).headers(responseHeaders).body(response.getBody());
	}

}
