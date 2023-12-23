package com.eun.tutorial.api.v1.strategy.stock.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.eun.tutorial.api.v1.constant.Constants;
import com.eun.tutorial.api.v1.dto.ApiRequest;
import com.eun.tutorial.api.v1.dto.stock.StockItem;
import com.eun.tutorial.api.v1.dto.stock.StockSearchRequest;
import com.eun.tutorial.api.v1.factory.stock.location.LocationStrategyFactory;
import com.eun.tutorial.api.v1.strategy.stock.location.LocationStrategy;

import lombok.RequiredArgsConstructor;

@Component(Constants.SEARCH_BY_MODEL)
@RequiredArgsConstructor
public class ModelStrategy implements TypeStrategy {
	private final LocationStrategyFactory strategyFactory;

	@Override
	public List<StockItem> searchStock(ApiRequest<StockSearchRequest> request) {
	    // HashSet을 사용하여 중복 제거
	    Set<StockItem> uniqueStockItems = new HashSet<>(request.getData().getStockItems());
	    
	    // 중복이 제거된 리스트를 stockSearchRequest에 다시 설정
	    request.getData().setStockItems(new ArrayList<>(uniqueStockItems));

	    LocationStrategy strategy = strategyFactory
	            .getStrategy(request.getData().getStockHeader().getStockSearchLocation());
	    return strategy.searchStock(request); // Set에 있는 항목들을 ArrayList로 변환
	}
}
