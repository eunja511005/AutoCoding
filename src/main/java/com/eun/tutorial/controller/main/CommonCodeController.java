package com.eun.tutorial.controller.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.CommonCodeDTO;
import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.exception.ErrorCode;
import com.eun.tutorial.service.main.CommonCodeService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/commonCode")
@Slf4j
@AllArgsConstructor
public class CommonCodeController {
	
	private final CommonCodeService commonCodeService;
	
    @GetMapping("/list")
    public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/commonCode");
		return modelAndView;
    }
    
    @PostMapping("/list")
    public @ResponseBody List<CommonCodeDTO> getCommonCodeList() {
        return commonCodeService.getCommonCodeList();
    } 
    
	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id){
	    try {
	    	CommonCodeDTO commonCodeDTOList = commonCodeService.getCommonCodeListById(id);
	        return new ApiResponse<>(true, "Successfully retrieved the common code list.", commonCodeDTOList);
	    } catch (Exception e) {
	        return new ApiResponse<>(false, "Failed to retrieve the common code list.", null);
	    }
	}
    
    @PostMapping("/save")
    public @ResponseBody ApiResponse saveCommonCode(CommonCodeDTO commonCodeDTO) {
        try {
            commonCodeService.saveCommonCode(commonCodeDTO);
            return new ApiResponse<Boolean>(true, "Success message", null);
        } catch (Exception e) {
        	return new ApiResponse<Boolean>(false, "Error message", null);
        }
    }
    
    @DeleteMapping("/{id}")
    public @ResponseBody ApiResponse deleteCommonCode(@PathVariable Long id) {
        try {
            commonCodeService.deleteCommonCode(id);
            return new ApiResponse<Boolean>(true, "Successfully deleted the common code data.", null);
        } catch (Exception e) {
            return new ApiResponse<Boolean>(false, "Failed to delete the common code data.", null);
        }
    }
    
    @GetMapping("/{codeGroup}")
    public @ResponseBody ApiResponse getCommonCodesByCategory(@PathVariable String codeGroup) throws CustomException {
        try {
            return new ApiResponse<>(true, "Successfully search the common code data.", commonCodeService.getCommonCodesByCategory(codeGroup));
        } catch (Exception e) {
        	throw new CustomException(ErrorCode.NO_COMMON_CODE, e);
//            return new ApiResponse<>(false, "Failed to search the common code data.", null);
        }
    }

}
