package com.eun.tutorial.api.v1.dto.stock;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.eun.tutorial.api.v1.dto.AdditionalField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockRegisterRequest {
	
	@NotEmpty(message = "At least one stock item is required")
	private List<StockItem> stockItems;
	
	@Valid // AdditionalField 객체 내의 필드에 대한 유효성 검증을 활성화
	private List<AdditionalField> additionalField;
}
