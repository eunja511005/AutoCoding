package com.eun.tutorial.api.v1.domain;

import lombok.Data;

@Data
public class Warehouse {
	private String id;
    private String apiEndpoint; // 창고 시스템 호출 주소
    
	public Warehouse(String id, String apiEndpoint) {
		super();
		this.id = id;
		this.apiEndpoint = apiEndpoint;
	}
}
