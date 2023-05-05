package com.eun.tutorial.exception;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.service.ZthhErrorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends BasicErrorController {
	
	@Autowired
	private ZthhErrorService zthhErrorService;

    public CustomErrorController(ErrorAttributes errorAttributes,
                                 ServerProperties serverProperties,
                                 List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        
		HttpStatus status = getStatus(request);
		ModelAndView modelAndView = new ModelAndView();
		
		Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
		saveZthhError(body);
		
		switch (status){
			case UNAUTHORIZED:
				modelAndView.setViewName("jsp/error/401");
				break;
			case FORBIDDEN:
				modelAndView.setViewName("jsp/error/403");
				break;	
			case NOT_FOUND:
				modelAndView.setViewName("jsp/error/404");
				break;
			case INTERNAL_SERVER_ERROR:
				modelAndView.setViewName("jsp/error/500");
				break;
			case SERVICE_UNAVAILABLE:
				modelAndView.setViewName("jsp/error/503");
				break;
		}
        
        return modelAndView;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        
		HttpStatus status = getStatus(request);
		if (status == HttpStatus.NO_CONTENT) {
			return new ResponseEntity<>(status);
		}
		
		Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
		saveZthhError(body);
		
		return new ResponseEntity<>(body, status);
        
    }
    
    private void saveZthhError(Map<String, Object> errorMap) {
    	zthhErrorService.save(ZthhErrorDTO.builder()
                .errorMessage("CustomErrorController Error : " + errorMap.toString())
                .build()
        );
    }
}