package com.eun.tutorial.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field {
    private String name;
    private Class<?> type;
}