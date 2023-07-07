package com.eun.tutorial.service.external.pubdata;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.eun.tutorial.dto.external.pubdata.RealEstatePriceItem;
import com.eun.tutorial.dto.external.pubdata.RequestDetails;
import com.eun.tutorial.dto.external.pubdata.TourismStayingRootDTO;
import com.eun.tutorial.dto.external.pubdata.TourismStayingRootDTO.RowDTO;
import com.eun.tutorial.dto.main.ApiMasterDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableResult;
import com.eun.tutorial.service.main.ApiMasterService;
import com.eun.tutorial.util.DataTableUtil;
import com.eun.tutorial.util.RestTemplateCallUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourismStayingServiceImpl implements TourismStayingService{
	
	private final RestTemplateCallUtil restTemplateCallUtil;
	private final ApiMasterService apiMasterService;

	@Override
	public DataTableResult<RowDTO> searchTourismStaying(DataTableRequestDTO searchDTO) throws JsonProcessingException, URISyntaxException {
		String apiName = "tourismStaying";
        String systemName = "Gyeonggi";
		
		ApiMasterDTO apiMasterDTO = getCallInformation(apiName, systemName);
		List<RowDTO> rowDTOList = getTourizmStayingInfo2(apiMasterDTO, searchDTO);
		int totalSize = rowDTOList.size();
		
		return DataTableUtil.getResult(searchDTO, rowDTOList, totalSize);
	}
	
	public ApiMasterDTO getCallInformation(String apiName, String systemName){
		ApiMasterDTO apiMasterDTO = apiMasterService.getApiMasterByAPIAndSystemName(apiName, systemName);
		return apiMasterDTO;
	}
	
	public Map<String, Object> getTourizmStayingInfo(ApiMasterDTO apiMasterDTO) throws RestClientException, URISyntaxException, JsonMappingException, JsonProcessingException{
		Map<String, Object> resMap = new HashMap<>();
		
		StringBuilder sb = new StringBuilder();
		sb.append(apiMasterDTO.getCallUrl());
		sb.append("?Type=json");
		sb.append("&pIndex=1");
		sb.append("&pSize=10");
		
        // RequestDetails 객체 생성
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
		
		ResponseEntity<String> response = restTemplateCallUtil.sendRequest(requestDetails);
		
		String jsonString = response.getBody();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
        // JSON 문자열을 Map으로 변환
        Map<String, Object> resultMap = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});

        // "row" 속성을 List로 변환
        List<Map<String, Object>> TourismStayingList = (List<Map<String, Object>>) resultMap.get("TourismStaying");
        int totalCount = 0;
        List<Map<String, Object>> rowList = new ArrayList<>();
        for (Map<String, Object> map : TourismStayingList) {
        	if(map.get("head")!=null) {
        		List<Map<String, Object>> headerList = (List<Map<String, Object>>) map.get("head");
        		for (Map<String, Object> map2 : headerList) {
        			if(map2.get("list_total_count")!=null) {
        				totalCount = (int) map2.get("list_total_count");
        			}
				}
        		totalCount = (int) headerList.get(0).get("list_total_count");
        	}else if(map.get("row")!=null) {
        		rowList = (List<Map<String, Object>>) map.get("row");
        		for (Map<String, Object> map2 : rowList) {
        			map2.get("SIGUN_CD");
        			map2.get("SIGUN_NM");
        			map2.get("BIZPLC_NM");
        			map2.get("LICENSG_DE");
        			map2.get("BSN_STATE_NM");
        			map2.get("BULDNG_PURPOS_NM");
        			map2.get("LOCPLC_FACLT_TELNO");
        			map2.get("CULTUR_PHSTRN_INDUTYPE_NM");
        			map2.get("ROADNM_ZIP_CD");
        			map2.get("REGION_DIV_NM");
        			map2.get("TOT_FLOOR_CNT");
        			map2.get("REFINE_LOTNO_ADDR");
        			map2.get("REFINE_ROADNM_ADDR");
				}
        	}
		}
        
        resMap.put("total", totalCount);
        resMap.put("rowList", rowList);
		
		return resMap;
	}
	
	public List<RowDTO> getTourizmStayingInfo2(ApiMasterDTO apiMasterDTO, DataTableRequestDTO searchDTO) throws RestClientException, URISyntaxException, JsonMappingException, JsonProcessingException{
		
		StringBuilder sb = new StringBuilder();
		sb.append(apiMasterDTO.getCallUrl());
		sb.append("?Key="+apiMasterDTO.getAuthor());
		sb.append("&Type=json");
		sb.append("&SIGUN_CD="+searchDTO.getSearch().get("sigunCd").getValue());
		sb.append("&pIndex="+(searchDTO.getStart()+1));
		sb.append("&pSize="+searchDTO.getLength());
		
        // RequestDetails 객체 생성
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
		
		ResponseEntity<String> response = restTemplateCallUtil.sendRequest(requestDetails);
		
		String jsonString = response.getBody();
		
		log.info(" ### response {}", sb.toString());
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		//objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		//objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		
        // JSON 문자열을 Map으로 변환
		TourismStayingRootDTO tourismStayingRootDTO = objectMapper.readValue(jsonString, new TypeReference<TourismStayingRootDTO>() {});

		int count = tourismStayingRootDTO.getTourismStaying().get(0).getHead().get(0).getList_total_count();
		List<RowDTO> rowDTOList = tourismStayingRootDTO.getTourismStaying().get(1).getRow();
		
		return rowDTOList;
	}

}
