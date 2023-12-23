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
		// TODO Auto-generated method stub
		return null;
	}

}
