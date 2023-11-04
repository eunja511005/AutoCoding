package com.eun.tutorial.controller.batch;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.batch.BatchMasterDTO;
import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableResult;
import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.exception.ErrorCode;
import com.eun.tutorial.service.batch.BatchMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/batchMaster")
@Slf4j
@AllArgsConstructor
public class BatchMasterController {
    
	private final BatchMasterService batchMasterService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/batchMaster");
		return modelAndView;
	}
    
    @PostMapping("/list")
    public @ResponseBody DataTableResult<BatchMasterDTO> list(@RequestBody DataTableRequestDTO searchDTO) throws JsonProcessingException {
        log.info(searchDTO.toString());
        
        List<BatchMasterDTO> batchMasterDTOs = batchMasterService.searchBatchMasters(searchDTO);
        int totalCount = batchMasterService.getTotalCount(searchDTO);
        
        return getResult(searchDTO, batchMasterDTOs, totalCount);
    }

	private DataTableResult<BatchMasterDTO> getResult(DataTableRequestDTO searchDTO, List<BatchMasterDTO> batchMasterDTOs, int totalCount) {
		DataTableResult<BatchMasterDTO> result = new DataTableResult<>();
        int start = searchDTO.getStart();
        int length = searchDTO.getLength();
        int end = start + length;
        
        int pageNum = (start / length) + 1;
        int totalPages = (int) Math.ceil((double)totalCount/length);
        result.setDraw(searchDTO.getDraw());
        result.setPageLength(length);
        result.setRecordsFiltered(totalCount);
        result.setStart(start);
        result.setEnd(end);
        result.setRecordsTotal(totalCount);
        result.setPage(pageNum);
        result.setPageTotal(totalPages);
        result.setData(batchMasterDTOs);
		return result;
	}
    
//	@PostMapping("/list/{id}")
//	public @ResponseBody ApiResponse<BatchMasterDTO> list(@PathVariable String id) {
//		log.info("Post List by ID : {}", id);
//		BatchMasterDTO layoutDTOList = batchMasterService.getBatchMasterById(id);
//		return new ApiResponse<>(true, "Successfully retrieved the layout list.", layoutDTOList);
//	}

	@PostMapping("/")
	public @ResponseBody ApiResponse<Object> saveLayout(@RequestBody List<BatchMasterDTO> batchMasterDTOList) throws CustomException {
        try {
        	Map<String, Integer> resMap = batchMasterService.saveBatchMaster(batchMasterDTOList);
            return new ApiResponse<>(true, "Insert : "+resMap.get("inserCount")+" , update : "+resMap.get("updateCount"), null);
        } catch (Exception e) {
        	throw new CustomException(ErrorCode.INTER_SERVER_ERROR, e);
        }
	}

	@DeleteMapping("/")
	public @ResponseBody ApiResponse<Object> deleteLayout(@RequestBody List<String> idList) throws CustomException {
        try {
        	Map<String, Integer> resMap = batchMasterService.deleteBatchMaster(idList);
            return new ApiResponse<>(true, "Delete "+resMap.get("deleteCount"), null);
        } catch (Exception e) {
        	throw new CustomException(ErrorCode.INTER_SERVER_ERROR, e);
        }
	}
}