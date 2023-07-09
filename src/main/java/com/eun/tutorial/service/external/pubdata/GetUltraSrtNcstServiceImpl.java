package com.eun.tutorial.service.external.pubdata;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.eun.tutorial.dto.external.pubdata.GetUltraSrtNcstRootDTO;
import com.eun.tutorial.dto.external.pubdata.GetUltraSrtNcstRootDTO.ItemDTO;
import com.eun.tutorial.dto.external.pubdata.RequestDetails;
import com.eun.tutorial.dto.main.ApiMasterDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableResult;
import com.eun.tutorial.service.main.ApiMasterService;
import com.eun.tutorial.util.DataTableUtil;
import com.eun.tutorial.util.DateUtils;
import com.eun.tutorial.util.RestTemplateCallUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetUltraSrtNcstServiceImpl implements GetUltraSrtNcstService{
	
	private final RestTemplateCallUtil restTemplateCallUtil;
	private final ApiMasterService apiMasterService;

	@Override
	public DataTableResult<ItemDTO> searchGetUltraSrtNcst(DataTableRequestDTO searchDTO) throws JsonProcessingException, URISyntaxException {
		String apiName = "getUltraSrtNcst";
		String systemName = "PublicPortal";
		
		ApiMasterDTO apiMasterDTO = apiMasterService.getApiMasterByAPIAndSystemName(apiName, systemName);
		
		List<ItemDTO> rowDTOList = getApiResult(apiMasterDTO, searchDTO);
		int totalSize = rowDTOList.size();
		
		return DataTableUtil.getResult(searchDTO, rowDTOList, totalSize);
	}
	
	private List<ItemDTO> getApiResult(ApiMasterDTO apiMasterDTO, DataTableRequestDTO searchDTO) throws RestClientException, URISyntaxException, JsonProcessingException{
		
		String sixAgo = DateUtils.getSomeHourAgo(1);
		String date = sixAgo.substring(0, 8);
		String hour = sixAgo.substring(8, 10)+"00";
		
		StringBuilder sb = new StringBuilder();
		sb.append(apiMasterDTO.getCallUrl());
		sb.append("?serviceKey="+apiMasterDTO.getAuthor());
		sb.append("&dataType=json");
		sb.append("&pageNo="+(searchDTO.getStart()+1));
		sb.append("&numOfRows="+searchDTO.getLength());
		sb.append("&base_date="+date);
		sb.append("&base_time="+hour);
		sb.append("&nx="+searchDTO.getSearch().get("ncatLoc").getValue().split(",")[0]);
		sb.append("&ny="+searchDTO.getSearch().get("ncatLoc").getValue().split(",")[1]);
		
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
		
        // JSON 문자열을 Map으로 변환
		GetUltraSrtNcstRootDTO getUltraSrtNcstRootDTO = objectMapper.readValue(jsonString, new TypeReference<GetUltraSrtNcstRootDTO>() {});

		return getUltraSrtNcstRootDTO.getResponse().getBody().getItems().getItem();
	}

}
