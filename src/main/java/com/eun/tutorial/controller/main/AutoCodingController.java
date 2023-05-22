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
import com.eun.tutorial.dto.main.AutocodingFieldDTO;
import com.eun.tutorial.dto.main.Field;
import com.eun.tutorial.service.main.AutoCodingService;
import com.eun.tutorial.service.main.CodeGenerator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/autoCoding")
@Slf4j
@AllArgsConstructor
public class AutoCodingController {
	
	private final AutoCodingService autoCodingService;
	private final CodeGenerator codeGenerator;
	
    @GetMapping("/list")
    public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/autoCoding");
		return modelAndView;
    }
    
    @PostMapping("/list")
    public @ResponseBody List<AutocodingFieldDTO> getCommonCodeList() {
        return autoCodingService.getAutoCodingList();
    } 
    
    @PostMapping("/generate")
    public @ResponseBody ApiResponse generate(@RequestBody ApiRequest<List<AutocodingFieldDTO>> apiRequest) {

		log.info("Post List by ID : {}", apiRequest.getSubject());

		if (apiRequest.getSubject() == null || apiRequest.getSubject().isEmpty()) {
			throw new IllegalArgumentException("Subject cannot be null or empty.");
		}

		List<Field> fields = new ArrayList<>();
		Field field = null;
		for (AutocodingFieldDTO autocodingFieldDTO : apiRequest.getData()) {
			field = new Field(autocodingFieldDTO.getFieldName(), autocodingFieldDTO.getFieldType());
			fields.add(field);
		}

		List<AutoCodingDTO> autoCodingList = new ArrayList<>();
		AutoCodingDTO autoCodingDTO = codeGenerator.generateDTOClass(fields, apiRequest.getSubject());
		AutoCodingDTO autoCodingController = codeGenerator.generateControllerClass(apiRequest.getSubject());
		AutoCodingDTO autoCodingServiceFile = codeGenerator.generateServiceClass(apiRequest.getSubject());
		AutoCodingDTO autoCodingServiceImpl = codeGenerator.generateServiceImplClass(apiRequest.getSubject());
		AutoCodingDTO autoCodingMapper = codeGenerator.generateMapperClass(apiRequest.getSubject());
		AutoCodingDTO autoCodingMapperXml = codeGenerator.generateMapperXml(fields, apiRequest.getSubject());
		AutoCodingDTO autoCodingSchemaSql = codeGenerator.generateSchemaSql(fields, apiRequest.getSubject());
		AutoCodingDTO autoCodingJsp = codeGenerator.generateJsp(fields, apiRequest.getSubject());
		AutoCodingDTO autoCodingJs = codeGenerator.generateJs(fields, apiRequest.getSubject());
		autoCodingList.add(autoCodingDTO);
		autoCodingList.add(autoCodingController);
		autoCodingList.add(autoCodingServiceFile);
		autoCodingList.add(autoCodingServiceImpl);
		autoCodingList.add(autoCodingMapper);
		autoCodingList.add(autoCodingMapperXml);
		autoCodingList.add(autoCodingSchemaSql);
		autoCodingList.add(autoCodingJsp);
		autoCodingList.add(autoCodingJs);

		return new ApiResponse<>(true, "Successfully search the common code data.", autoCodingList);
	}
    
	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id){
	    try {
	    	AutocodingFieldDTO autocodingFieldDTO = autoCodingService.getAutoCodingListById(id);
	        return new ApiResponse<>(true, "Successfully retrieved the common code list.", autocodingFieldDTO);
	    } catch (Exception e) {
	        return new ApiResponse<>(false, "Failed to retrieve the common code list.", null);
	    }
	}
    
    @PostMapping("/save")
    public @ResponseBody ApiResponse saveCommonCode(@RequestBody AutocodingFieldDTO autocodingFieldDTO) {
    	autoCodingService.saveAutoCoding(autocodingFieldDTO);
        return new ApiResponse<Boolean>(true, "Success message", null);
    }
    
    @DeleteMapping("/{id}")
	public @ResponseBody ApiResponse deleteAutoCoding(@PathVariable String id) {
		autoCodingService.deleteAutoCoding(id);
		return new ApiResponse<Boolean>(true, "Successfully deleted the common code data.", null);
	}

}
