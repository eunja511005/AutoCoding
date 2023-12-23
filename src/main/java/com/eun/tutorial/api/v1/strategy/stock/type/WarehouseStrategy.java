package com.eun.tutorial.api.v1.strategy.stock.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.eun.tutorial.api.v1.constant.Constants;
import com.eun.tutorial.api.v1.dto.ApiRequest;
import com.eun.tutorial.api.v1.dto.stock.StockHeader;
import com.eun.tutorial.api.v1.dto.stock.StockItem;
import com.eun.tutorial.api.v1.dto.stock.StockSearchRequest;
import com.eun.tutorial.api.v1.factory.stock.location.LocationStrategyFactory;
import com.eun.tutorial.api.v1.strategy.stock.location.LocationStrategy;

import lombok.RequiredArgsConstructor;

@Component(Constants.SEARCH_BY_WAREHOUSE)
@RequiredArgsConstructor
public class WarehouseStrategy implements TypeStrategy {

	private final LocationStrategyFactory strategyFactory;

	@Override
	public List<StockItem> searchStock(ApiRequest<StockSearchRequest> request) {
	    Set<StockItem> uniqueStockItems = new HashSet<>(); // HashSet을 사용하여 중복 제거
	    for (StockItem stockItem : request.getData().getStockItems()) {
	        uniqueStockItems.add(StockItem.builder()
	            .plant(stockItem.getPlant())
	            .storageLocation(stockItem.getStorageLocation())
	            .build());
	    }

	    // 중복이 제거된 리스트를 stockSearchRequest에 다시 설정
	    request.getData().setStockItems(new ArrayList<>(uniqueStockItems));

	    LocationStrategy strategy = strategyFactory
	            .getStrategy(request.getData().getStockHeader().getStockSearchLocation());
	    return strategy.searchStock(request); 
	}

}
