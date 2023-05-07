package com.eun.tutorial.dto.main;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableResult<T> {
	private int start;
	private int end;
	private int page;
	private int pageLength;
	private int pageTotal;
    private int draw; // DataTable에서 넘겨주는 draw 값
    private int recordsTotal; // 총 데이터 건수
    private int recordsFiltered; // 검색 후 건수
    private List<T> data; // 조회된 데이터
    private String error; // 에러 메시지 (있을 경우)
}
