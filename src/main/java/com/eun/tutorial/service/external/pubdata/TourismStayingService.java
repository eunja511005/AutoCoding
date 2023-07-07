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
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestClientException;

import com.eun.tutorial.dto.external.pubdata.RealEstatePriceItem.ItemsDTO;
import com.eun.tutorial.dto.external.pubdata.TourismStayingRootDTO.RowDTO;
import com.eun.tutorial.dto.main.ApiMasterDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface TourismStayingService {
	DataTableResult<RowDTO> searchTourismStaying(DataTableRequestDTO searchDTO) throws JsonProcessingException, URISyntaxException;
	ApiMasterDTO getCallInformation(String apiName, String systemName);
	Map getTourizmStayingInfo(ApiMasterDTO apiMasterDTO) throws RestClientException, URISyntaxException, JsonMappingException, JsonProcessingException;
	List<RowDTO> getTourizmStayingInfo2(ApiMasterDTO apiMasterDTO, DataTableRequestDTO searchDTO) throws RestClientException, URISyntaxException, JsonMappingException, JsonProcessingException;
}