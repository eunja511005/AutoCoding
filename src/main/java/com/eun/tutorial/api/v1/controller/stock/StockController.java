package com.eun.tutorial.api.v1.controller.stock;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eun.tutorial.api.v1.dto.ApiRequest;
import com.eun.tutorial.api.v1.dto.ApiResponse;
import com.eun.tutorial.api.v1.dto.stock.StockRegisterRequest;
import com.eun.tutorial.api.v1.dto.stock.StockSearchRequest;
import com.eun.tutorial.api.v1.dto.stock.StockSearchResponse;
import com.eun.tutorial.api.v1.exception.CustomException;
import com.eun.tutorial.api.v1.service.StockService;
import com.eun.tutorial.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stock")
public class StockController {
	private final StockService stockService;
	private final FileUtil fileUtil;
	
	/**
	 * 
	 * @param request
	 * @return
	 * Spring Validation을 통해 객체의 필드에 대한 입력 검증
	 * spring-boot-starter-validation 의존성 추가 필요(<artifactId>spring-boot-starter-validation</artifactId>)
	 * DTO에 @NotBlank(message = "Item category is required"), @NotNull(message = "Item number is required") 사용
	 * Controller에서 @Validated @RequestBody 이렇게 사용
	 * 유효성 검사에 실패한 경우 MethodArgumentNotValidException 발생
	 * MethodArgumentNotValidException 발생시 GlobalExceptionHander에서 공통으로 오류 메세지 리턴 함
	 * 
	 */
    @PostMapping("/search/v1")
    public ResponseEntity<ApiResponse<StockSearchResponse>> searchStock(@Validated @RequestBody ApiRequest<StockSearchRequest> request) {
    	try {
            // ApiRequest 객체를 사용한 비즈니스 로직 처리
            log.info("Received data: " + request);
            
            StockSearchResponse stockSearchResponse = stockService.searchStock(request);

            // 처리 결과 반환
            ApiResponse<StockSearchResponse> response = ApiResponse.success(stockSearchResponse);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ApiResponse<StockSearchResponse> errorResponse = ApiResponse.failure(e.getErrorCode(), e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }

    }
    
    @PostMapping("/register/v1")
    public ResponseEntity<ApiResponse<String>> registerStock(
            @Validated @RequestPart("apiRequest") ApiRequest<StockRegisterRequest> apiRequest,
            MultipartHttpServletRequest multipartFiles) {

        try {
            // 재고 데이터 처리 로직
            // stockRequest 객체를 사용한 처리 로직
        	Map<String, Object> res;
        	String attachId = "";
            if (multipartFiles != null) {
            	res = fileUtil.saveImageWithTableWithPath(multipartFiles, "openImg/stock");
            	attachId = (String) res.get("attachId");
            	log.info(attachId);
            }

            // 비즈니스 로직 처리 후 성공 결과 반환
            ApiResponse<String> response = ApiResponse.success("Stock registered successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 에러 처리
            ApiResponse<String> errorResponse = ApiResponse.failure("INTERNAL_SERVER_ERROR", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
