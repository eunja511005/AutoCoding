package com.eun.tutorial.controller.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.ApiRequest;
import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.AutoCodingDTO;
import com.eun.tutorial.dto.main.CommonCodeDTO;
import com.eun.tutorial.dto.main.Field;
import com.eun.tutorial.service.main.CodeGenerator;
import com.eun.tutorial.service.main.CommonCodeService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/autoCoding")
@Slf4j
@AllArgsConstructor
public class AutoCodingController {
	
	private final CommonCodeService commonCodeService;
	private final CodeGenerator codeGenerator;
	
    @GetMapping("/list")
    public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/autoCoding");
		return modelAndView;
    }
    
    @PostMapping("/list")
    public @ResponseBody List<CommonCodeDTO> getCommonCodeList() {
        return commonCodeService.getCommonCodeList();
    } 
    
    @PostMapping("/generate")
    public @ResponseBody ApiResponse generate(@RequestBody ApiRequest<List<CommonCodeDTO>> apiRequest) {
    	
        try {
        	
        	log.info("Post List by ID : {}", apiRequest.getSubject());
        	
            if (apiRequest.getSubject() == null || apiRequest.getSubject().isEmpty()) {
                throw new IllegalArgumentException("Subject cannot be null or empty.");
            }
        	
        	
        	List<Field> fields = new ArrayList<>();
        	Field field = null;
        	for (CommonCodeDTO commonCodeDTOList : apiRequest.getData()) {
        		field = new Field(commonCodeDTOList.getCode(), String.class); 
        		fields.add(field);
    		}
            
            List<AutoCodingDTO> autoCodingList = new ArrayList<>();
            AutoCodingDTO autoCodingDTO = codeGenerator.generateDTOClass(fields, apiRequest.getSubject());
            AutoCodingDTO autoCodingController = codeGenerator.generateControllerClass(apiRequest.getSubject());
            AutoCodingDTO autoCodingService = codeGenerator.generateServiceClass(apiRequest.getSubject());
            AutoCodingDTO autoCodingServiceImpl = codeGenerator.generateServiceImplClass(apiRequest.getSubject());
            AutoCodingDTO autoCodingMapper = codeGenerator.generateMapperClass(apiRequest.getSubject());
            AutoCodingDTO autoCodingMapperXml = codeGenerator.generateMapperXml(fields, apiRequest.getSubject());
            autoCodingList.add(autoCodingDTO);
            autoCodingList.add(autoCodingController);
            autoCodingList.add(autoCodingService);
            autoCodingList.add(autoCodingServiceImpl);
            autoCodingList.add(autoCodingMapper);
            autoCodingList.add(autoCodingMapperXml);
            
            return new ApiResponse<>(true, "Successfully search the common code data.", autoCodingList);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Failed to search the common code data.", null);
        }
    	
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
    public @ResponseBody ApiResponse getCommonCodesByCategory(@PathVariable String codeGroup) {
        try {
            return new ApiResponse<>(true, "Successfully search the common code data.", commonCodeService.getCommonCodesByCategory(codeGroup));
        } catch (Exception e) {
            return new ApiResponse<>(false, "Failed to search the common code data.", null);
        }
    }

}
