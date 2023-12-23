package com.eun.tutorial.api.v1.strategy.stock.location;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.eun.tutorial.api.v1.constant.Constants;
import com.eun.tutorial.api.v1.domain.Warehouse;
import com.eun.tutorial.api.v1.dto.ApiRequest;
import com.eun.tutorial.api.v1.dto.stock.StockItem;
import com.eun.tutorial.api.v1.dto.stock.StockSearchRequest;

@Component(Constants.SEARCH_FROM_REMOTE)
public class RemoteStrategy implements LocationStrategy {

	@Override
	public List<StockItem> searchStock(ApiRequest<StockSearchRequest> request) {
		List<Warehouse> warehouseList = getWarehouses(request.getCommonHeader().getClientId());
        List<StockItem> availableStock = new ArrayList<>();

        for (Warehouse warehouse : warehouseList) {
            List<StockItem> stockFromWarehouse = getRemoteStocks(warehouse.getApiEndpoint());
            availableStock.addAll(stockFromWarehouse);
        }
        
        // TODO 예약 수량 확인 후 빼줘야 함.

        // 수량 기준으로 내림 차순으로 정렬
        availableStock.sort(Comparator.comparingInt(StockItem::getConfirmQuantity).reversed());
        
        return availableStock;
	}
	
    private List<Warehouse> getWarehouses(String clientId) {
    	
    	// TODO clientId(온라인 스토어의 SystemId)를 이용하여 warehouse 리스트를 DB에서 조회해야 한다.
    	
        // 샘플 창고 데이터 생성
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(new Warehouse("Warehouse1", "http://api.warehouse1.com"));
        warehouses.add(new Warehouse("Warehouse2", "http://api.warehouse2.com"));
        return warehouses;
    }
    
    private List<StockItem> getRemoteStocks(String apiEndpoint) {
    	
    	// TODO BackEnd 시스템의 apiEndpoint 주소 호출해서 가용 재고를 조회한다.
    	
        // 샘플 재고 데이터 생성
        List<StockItem> stockItems = new ArrayList<>();
        
        stockItems.add(StockItem.builder()
        		.modelCode("M1")
        		.plant("P1")
        		.storageLocation("S1")
        		.valuationType("T1")
        		.vendorCode("V1")
        		.confirmQuantity(1)
        		.build());
        stockItems.add(StockItem.builder()
        		.modelCode("M2")
        		.plant("P2")
        		.storageLocation("S2")
        		.valuationType("T2")
        		.vendorCode("V2")
        		.confirmQuantity(2)
        		.build());
        return stockItems; // 임시 반환
    }

}
