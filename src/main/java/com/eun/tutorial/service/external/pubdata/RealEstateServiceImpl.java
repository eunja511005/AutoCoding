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

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.external.pubdata.RealEstatePriceItem;
import com.eun.tutorial.dto.external.pubdata.RealEstatePriceItem.ItemsDTO;
import com.eun.tutorial.dto.external.pubdata.RealEstatePriceItem.RealEstatePriceItemDTO;
import com.eun.tutorial.dto.external.pubdata.RequestDetails;
import com.eun.tutorial.dto.main.ApiMasterDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.service.main.ApiMasterService;
import com.eun.tutorial.util.RestTemplateCallUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RealEstateServiceImpl implements RealEstatePriceService {

	private final RestTemplateCallUtil restTemplateCallUtil;
	private final ApiMasterService apiMasterService;

	@Override
	public ItemsDTO searchRealEstatePrice(DataTableRequestDTO searchDTO) throws JsonProcessingException, URISyntaxException {
		// 1. get call Information from UI.
        String lawCode = searchDTO.getSearch().get("lawCode").getValue();
        String searchMonth = searchDTO.getSearch().get("searchMonth").getValue();
        
        // 2. get Call Information from db table.
        String systemName = "PublicPortal";
        String apiName = "realEstatePrice";
		ApiMasterDTO apiMasterDTO = apiMasterService.getApiMasterByAPIAndSystemName(apiName, systemName);
        String endpointUrl = apiMasterDTO.getCallUrl();
        String serviceKey = apiMasterDTO.getAuthor();
    	
        // [중요] URI를 이용해서 호출해야 인코딩된 serviceKey가 정상적으로 공공포털로 넘어가 오류 안남
        endpointUrl = endpointUrl + "?serviceKey=" + serviceKey + "&LAWD_CD=" + lawCode + "&DEAL_YMD=" + searchMonth;

        HttpHeaders headers = new HttpHeaders();
        
        // RequestDetails 객체 생성
        RequestDetails<Object, RealEstatePriceItem> requestDetails = new RequestDetails<>();
        requestDetails.setEndpointUrl(endpointUrl);
        requestDetails.setHttpMethod(apiMasterDTO.getHttpMethod());
        requestDetails.setRequestBody(null);
        requestDetails.setHeaders(headers);
        requestDetails.setRequestMediaType(MediaType.APPLICATION_JSON);
        requestDetails.setResponseMediaType(MediaType.APPLICATION_JSON);
        requestDetails.setResponseType(RealEstatePriceItem.class);
        requestDetails.setLogYn(apiMasterDTO.getLogYn());
    	
    	// Send the request
    	ResponseEntity<RealEstatePriceItem> response = restTemplateCallUtil.sendRequest(requestDetails);
    	
    	List<RealEstatePriceItemDTO> itemList = response.getBody().getResponse().getBody().getItems().getItem();
    	int size = 0;
    	if(itemList!=null) {
    		size = itemList.size();
    	}
    	
    	List<RealEstatePriceItemDTO> transactionAmountSorted = getOrderedList(searchDTO, itemList);
    	
    	List<RealEstatePriceItemDTO> resultList = getPaginatedItems(transactionAmountSorted, searchDTO.getStart(), searchDTO.getLength());
    	
    	response.getBody().getResponse().getBody().getItems().setItem(resultList);
    	response.getBody().getResponse().getBody().getItems().setTotalSize(size);
		
    	return response.getBody().getResponse().getBody().getItems();
	}

	private List<RealEstatePriceItemDTO> getOrderedList(DataTableRequestDTO searchDTO, List<RealEstatePriceItemDTO> itemList) {
	    String orderColumnName = searchDTO.getOrderColumnName();
	    String orderCDirection = searchDTO.getOrderDirection();

	    List<RealEstatePriceItemDTO> sortedList = null; // 변수를 선언하고 null로 초기화

	    if (itemList != null) {
	        sortedList = new ArrayList<>(itemList); // 정렬할 리스트로 초기화
	        Comparator<RealEstatePriceItemDTO> comparator = null;

	        if ("거래금액".equals(orderColumnName)) {
	            comparator = Comparator.comparingDouble(RealEstatePriceItemDTO::getTransactionAmount);
	        } else if ("전용면적".equals(orderColumnName)) {
	            comparator = Comparator.comparingDouble(RealEstatePriceItemDTO::getExclusiveArea);
	        }

	        if (comparator != null) {
	            if ("DESC".equals(orderCDirection)) {
	                comparator = comparator.reversed();
	            }
	            sortedList.sort(comparator);
	        }
	    }

	    return sortedList;
	}
	
	public List<RealEstatePriceItemDTO> getPaginatedItems(List<RealEstatePriceItemDTO> itemList, int start, int pageSize) {
	    if (itemList == null) {
	        return Collections.emptyList(); // Return an empty list if itemList is null
	    }

	    int startIndex = start;
	    int endIndex = Math.min(startIndex + pageSize, itemList.size());
	    if (startIndex >= itemList.size()) {
	        return Collections.emptyList(); // Page is out of range, return an empty list.
	    }
	    return itemList.subList(startIndex, endIndex);
	}


}