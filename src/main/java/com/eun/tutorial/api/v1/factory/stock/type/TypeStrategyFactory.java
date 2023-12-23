package com.eun.tutorial.api.v1.factory.stock.type;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.eun.tutorial.api.v1.exception.ApiErrorCode;
import com.eun.tutorial.api.v1.exception.CustomException;
import com.eun.tutorial.api.v1.strategy.stock.type.TypeStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TypeStrategyFactory {
	private final Map<String, TypeStrategy> strategies;
	
    public TypeStrategy getStrategy(String searchType) {
    	TypeStrategy strategy = strategies.get(searchType);
        if (strategy == null) {
            throw new CustomException(ApiErrorCode.INVALID_REQUEST.getCode(), ApiErrorCode.INVALID_REQUEST.getMessage()+" : stockSearchLocation(L or R)");
        }
        return strategy;
    }
}
