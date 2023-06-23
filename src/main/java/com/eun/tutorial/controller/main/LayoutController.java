package com.eun.tutorial.controller.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableResult;
import com.eun.tutorial.dto.main.LayoutDTO;
import com.eun.tutorial.service.main.LayoutService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/layout")
@Slf4j
@AllArgsConstructor
public class LayoutController {
    
	private final LayoutService layoutService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/layout");
		return modelAndView;
	}
    
    @PostMapping("/list")
    public @ResponseBody DataTableResult<LayoutDTO> list(@RequestBody DataTableRequestDTO searchDTO) throws JsonProcessingException {
        log.info(searchDTO.toString());
        
        List<LayoutDTO> layoutDTOs = layoutService.searchLayouts(searchDTO);
        int totalCount = layoutService.getTotalCount(searchDTO);
        
        return getResult(searchDTO, layoutDTOs, totalCount);
    }

	private DataTableResult<LayoutDTO> getResult(DataTableRequestDTO searchDTO, List<LayoutDTO> layoutDTOs, int totalCount) {
		DataTableResult<LayoutDTO> result = new DataTableResult<>();
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
        result.setData(layoutDTOs);
		return result;
	}
    
	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse<LayoutDTO> list(@PathVariable String id) {
		log.info("Post List by ID : {}", id);
		LayoutDTO layoutDTOList = layoutService.getLayoutListById(id);
		return new ApiResponse<>(true, "Successfully retrieved the layout list.", layoutDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse<Object> saveLayout(@RequestBody LayoutDTO layoutDTO) {
		if (StringUtils.isBlank(layoutDTO.getId())) {
			layoutService.saveLayout(layoutDTO);
			return new ApiResponse<>(true, "Success save", null);
		} else {
			layoutService.updateLayout(layoutDTO);
			return new ApiResponse<>(true, "Success update", null);
		}
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ApiResponse<Object> deleteLayout(@PathVariable String id) {
		log.info("Delete by ID : {}", id);
		layoutService.deleteLayout(id);
		return new ApiResponse<>(true, "Successfully deleted the layout data.", null);
	}
	
	@PostMapping("/uploadFile")
	public @ResponseBody Map<String, Object> uploadImage(@RequestParam("files") List<MultipartFile> files) throws IOException {
	    Map<String, Object> response = new HashMap<>();
	    List<String> fileNames = new ArrayList<>();
	    List<String> filePaths = new ArrayList<>();
	    
	    for (MultipartFile file : files) {
	        fileNames.add(file.getOriginalFilename());
	        filePaths.add((String) layoutService.saveFile(file).get("createdFilePath"));
	    }
	    
	    response.put("message", "File upload successful");
	    response.put("fileNames", fileNames);
	    response.put("filePaths", filePaths);
	    
	    return response;
	}

}