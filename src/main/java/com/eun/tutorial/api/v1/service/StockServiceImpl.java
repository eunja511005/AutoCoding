package com.eun.tutorial.api.v1.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.api.v1.dto.ApiRequest;
import com.eun.tutorial.api.v1.dto.stock.StockHeader;
import com.eun.tutorial.api.v1.dto.stock.StockItem;
import com.eun.tutorial.api.v1.dto.stock.StockSearchRequest;
import com.eun.tutorial.api.v1.dto.stock.StockSearchResponse;
import com.eun.tutorial.api.v1.factory.stock.type.TypeStrategyFactory;
import com.eun.tutorial.api.v1.strategy.stock.type.TypeStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StockServiceImpl implements StockService {
	
	private final TypeStrategyFactory strategyFactory;

	@Override
	public StockSearchResponse searchStock(ApiRequest<StockSearchRequest> request) {
		
		// TODO 서비스 호출 시작, 종료시 로그 테이블에 저장 로직 추가 필요(로깅에 실패 하더라도 원래 기능은 동작 하도록 구현 필요)
		
		StockSearchRequest stockSearchRequest = request.getData();
		StockHeader stockHeader = stockSearchRequest.getStockHeader();

		List<StockItem> stockItemList = getAvailableStock(request);

		return new StockSearchResponse(stockHeader, stockItemList, null);
	}
	
    private List<StockItem> getAvailableStock(ApiRequest<StockSearchRequest> request) {
        
        TypeStrategy strategy = strategyFactory.getStrategy(request.getData().getStockHeader().getStockSearchType());
        return strategy.searchStock(request);

//        availableStock.sort(Comparator
//                .comparing(StockItem::getPlant)
//                .thenComparing(StockItem::getStorageLocation)
//                .thenComparing(Comparator.comparingInt(StockItem::getConfirmQuantity).reversed()));
    }

}
