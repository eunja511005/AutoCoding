package com.eun.tutorial.api.v1.service;

import com.eun.tutorial.api.v1.dto.ApiRequest;
import com.eun.tutorial.api.v1.dto.stock.StockSearchRequest;
import com.eun.tutorial.api.v1.dto.stock.StockSearchResponse;

public interface StockService {
	StockSearchResponse searchStock(ApiRequest<StockSearchRequest> request);
}
