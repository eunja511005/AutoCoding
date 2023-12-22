package com.eun.tutorial.api.v1.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdditionalField {
    @NotBlank(message = "Field name is required")
    private String fieldName;

    @NotBlank(message = "Field value is required")
    private String fieldValue;
}
