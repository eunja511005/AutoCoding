package com.eun.tutorial.api.v1.dto.stock;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.eun.tutorial.api.v1.dto.AdditionalField;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockSearchResponse {
	
	@NotNull(message = "Stock header is required")
	@Valid  // StockHeader 객체 내의 필드에 대한 유효성 검증을 활성화
	private StockHeader stockHeader;
	
	@NotEmpty(message = "At least one stock item is required")
	private List<StockItem> stockItems;
	
	@Valid // AdditionalField 객체 내의 필드에 대한 유효성 검증을 활성화
	private List<AdditionalField> additionalField;
}
