package com.eun.tutorial.dto.external.pubdata;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Data
public class RequestDetails<I, O> {
    private String systemName;
    private String apiName;
    private String endpointUrl;
    private String httpMethod;
    private I requestBody;
    private HttpHeaders headers;
    private MediaType requestMediaType;
    private MediaType responseMediaType;
    private Class<O> responseType;
    private String logYn;
}