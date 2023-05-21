package com.eun.tutorial.dto.main;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AutocodingFieldDTO {
    private String id;
    private String structureName;
    private String fieldName;
    private String description;
    private String fieldType;
    private String searchable;
    private String primaryKey;
    private String nullable;
    private String defaultValue;
    private Integer orderNumber;
    private String delYn;
    private String createId;
    private LocalDateTime createDt;
    private String updateId;
    private LocalDateTime updateDt;
}
