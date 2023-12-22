package com.eun.tutorial.api.v1.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRequest<T> {
	
	@NotNull(message = "CommonHeader is required")
	@Valid  // CommonHeader 객체 내의 필드에 대한 유효성 검증을 활성화
	private CommonHeader commonHeader;
	
	@NotNull(message = "Data is required")
	@Valid
    private T data;
}
