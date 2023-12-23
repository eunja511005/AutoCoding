package com.eun.tutorial.api.v1.strategy.stock.type;

import java.util.List;

import com.eun.tutorial.api.v1.dto.ApiRequest;
import com.eun.tutorial.api.v1.dto.stock.StockItem;
import com.eun.tutorial.api.v1.dto.stock.StockSearchRequest;

public interface TypeStrategy {
	List<StockItem> searchStock(ApiRequest<StockSearchRequest> request);
}
