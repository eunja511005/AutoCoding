package com.eun.tutorial.api.v1.strategy.stock.location;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eun.tutorial.api.v1.constant.Constants;
import com.eun.tutorial.api.v1.dto.ApiRequest;
import com.eun.tutorial.api.v1.dto.stock.StockItem;
import com.eun.tutorial.api.v1.dto.stock.StockSearchRequest;

@Component(Constants.SEARCH_FROM_LOCAL)
public class LocalStrategy implements LocationStrategy {

	@Override
	public List<StockItem> searchStock(ApiRequest<StockSearchRequest> request) {
		// TODO 로컬 DB에서 가용 재고를 조회 한다.(CustomException 이용하여 예외 발생시 꼭 오류 정보 리턴 하도록 구현)
		
		// TODO 조회한 가용 재고에서 예약 된 수량을 빼 준다.
		
		// TODO 수량 기준으로 내림차순으로 정렬 한다.
		
		return null;
	}

}
