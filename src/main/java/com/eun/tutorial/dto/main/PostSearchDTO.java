package com.eun.tutorial.dto.main;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchDTO {
    private Integer draw;
    private Integer start;
    private Integer length;
    private Map<String,PostSearch> search;
    private String orderColumnName;
    private String orderDirection;
    private Integer page;
    private Integer size;

    @Data
    public static class PostSearch {
        private String value;
        private Boolean regex;
    }

}

