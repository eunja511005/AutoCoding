package com.eun.tutorial.api.v1.dto.stock;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockHeader {
	@NotBlank(message = "StockSearchLocation is required")
	private String stockSearchLocation; // L : SEARCH_FROM_LOCAL, R : SEARCH_FROM_REMOTE
	
	@NotBlank(message = "StockSearchType is required")
	private String stockSearchType; // W : SEARCH_BY_WAREHOUSE, M : SEARCH_BY_MODEL

    private String reservationNo;
}
