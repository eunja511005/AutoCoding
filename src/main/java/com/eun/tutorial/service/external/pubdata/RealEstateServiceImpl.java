/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the @autoCoding.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The @autoCoding shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

package com.eun.tutorial.service.external.pubdata;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.main.ApiMasterDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.RealEstatePriceItem;
import com.eun.tutorial.dto.main.RealEstatePriceItem.RealEstatePriceItemDTO;
import com.eun.tutorial.service.main.ApiMasterService;
import com.eun.tutorial.util.MessageUtil;
import com.eun.tutorial.util.RestTemplateCallUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RealEstateServiceImpl implements RealEstatePriceService {

	private final RestTemplateCallUtil restTemplateCallUtil;
	private final ApiMasterService apiMasterService;
	private final MessageUtil messageUtil;
	

	@Override
	public List<RealEstatePriceItemDTO> searchRealEstatePrice(DataTableRequestDTO searchDTO) throws JsonProcessingException, URISyntaxException {
		// 1. get call Information from UI.
        String lawCode = searchDTO.getSearch().get("lawCode").getValue();
        String searchMonth = searchDTO.getSearch().get("searchMonth").getValue();
        
        // 2. get Call Information from db table.
        String systemName = "PublicPortal";
        String apiName = "realEstatePrice";
		ApiMasterDTO apiMasterDTO = apiMasterService.getApiMasterByAPIAndSystemName(apiName, systemName);
        String endpointUrl = apiMasterDTO.getCallUrl();
        String serviceKey = apiMasterDTO.getAuthor();
        HttpMethod method;
        if(apiMasterDTO.getHttpMethod().equals("GET")) {
        	method = HttpMethod.GET;
        }else if(apiMasterDTO.getHttpMethod().equals("POST")) {
        	method = HttpMethod.POST;
        }else {
        	String unknownHttpMethodMsg = messageUtil.getMessage("api.master.unknown.http.method");
            throw new IllegalArgumentException(unknownHttpMethodMsg);
        }
    	
        // [중요] URI를 이용해서 호출해야 인코딩된 serviceKey가 정상적으로 공공포털로 넘어가 오류 안남
        endpointUrl = endpointUrl + "?serviceKey=" + serviceKey + "&LAWD_CD=" + lawCode + "&DEAL_YMD=" + searchMonth;

        URI uri = new URI(endpointUrl);
        Object requestBody = null;
        HttpHeaders headers = new HttpHeaders();
        MediaType requestMediaType = MediaType.APPLICATION_JSON;
        MediaType responseMediaType = MediaType.APPLICATION_JSON;
        Class<RealEstatePriceItem> responseType = RealEstatePriceItem.class;
    	
    	// Send the request
    	ResponseEntity<RealEstatePriceItem> response = restTemplateCallUtil.sendRequest(uri, method, requestBody, headers,
    			requestMediaType, responseMediaType, responseType);
		
		return response.getBody().getResponse().getBody().getItems().getItem();
	}

}