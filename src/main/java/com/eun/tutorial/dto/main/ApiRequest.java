package com.eun.tutorial.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRequest<T> {
    private String subject;
    private T data;
}

