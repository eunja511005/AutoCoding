package com.eun.tutorial.service.external.pubdata;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import com.eun.tutorial.dto.external.pubdata.TourismStayingRootDTO.RowDTO;
import com.eun.tutorial.dto.main.ApiMasterDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO.PostSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@SpringBootTest
//@Transactional
class TourismStayingServiceImplTest {

	@Autowired
	TourismStayingService tourismStayingService;
	
	@Test
	void getCallInformation_with_apiName_systemName_getInformationSuccessfully() throws URISyntaxException {
		// 1. Given
		String apiName = "tourismStaying";
        String systemName = "Gyeonggi";
		
		// 2. When
        ApiMasterDTO apiMasterDTO = tourismStayingService.getCallInformation(apiName, systemName);
		
		// 3. Then
		assertEquals(apiMasterDTO.getCallUrl(), "https://openapi.gg.go.kr/TourismStaying");
		assertEquals(apiMasterDTO.getCallMax(), 10000);
		assertEquals(apiMasterDTO.getHttpMethod(), "GET");
		assertEquals(apiMasterDTO.getLogYn(), "Y");
	}
	
	@Test
	void getTourizmStayingInfo_with_apiMasterDTO_getInformationSuccessfully() throws URISyntaxException, JsonMappingException, RestClientException, JsonProcessingException {
		// 1. Given
		String apiName = "tourismStaying";
		String systemName = "Gyeonggi";
		ApiMasterDTO apiMasterDTO = tourismStayingService.getCallInformation(apiName, systemName);
		
		// 2. When
		Map<String, Object> resMap = tourismStayingService.getTourizmStayingInfo(apiMasterDTO);
		
		// 3. Then
		assertEquals(resMap.get("total"), 336);
		assertEquals(((List)resMap.get("rowList")).size(), 5);
	}
	
	@Test
	void getTourizmStayingInfo2_with_apiMasterDTO_getInformationSuccessfully() throws URISyntaxException, JsonMappingException, RestClientException, JsonProcessingException {
		// 1. Given
		String apiName = "tourismStaying";
		String systemName = "Gyeonggi";
		ApiMasterDTO apiMasterDTO = tourismStayingService.getCallInformation(apiName, systemName);
		
		DataTableRequestDTO searchDTO = new DataTableRequestDTO();
		Map<String, PostSearch> searchMap = new HashMap<>();
		PostSearch postSearch = new PostSearch();
		postSearch.setValue("41110");
		postSearch.setRegex(false);
		searchMap.put("sigunCd", postSearch);
		searchDTO.setStart(0);
		searchDTO.setLength(10);
		searchDTO.setSearch(searchMap );
		
		// 2. When
		List<RowDTO> rowDTOList = tourismStayingService.getTourizmStayingInfo2(apiMasterDTO, searchDTO);
		
		// 3. Then
		assertEquals(rowDTOList.size(), 10);
	}
	
}
