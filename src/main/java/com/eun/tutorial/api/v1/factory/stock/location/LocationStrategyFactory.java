package com.eun.tutorial.api.v1.factory.stock.location;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.eun.tutorial.api.v1.exception.ApiErrorCode;
import com.eun.tutorial.api.v1.exception.CustomException;
import com.eun.tutorial.api.v1.strategy.stock.location.LocationStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocationStrategyFactory {
	private final Map<String, LocationStrategy> strategies;
	
    public LocationStrategy getStrategy(String searchLocation) {
        LocationStrategy strategy = strategies.get(searchLocation);
        if (strategy == null) {
            throw new CustomException(ApiErrorCode.INVALID_REQUEST.getCode(), ApiErrorCode.INVALID_REQUEST.getMessage()+" : stockSearchLocation(L or R)");
        }
        return strategy;
    }
}
