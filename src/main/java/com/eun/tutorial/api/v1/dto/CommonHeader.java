package com.eun.tutorial.api.v1.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonHeader {
    @NotBlank(message = "Client ID is required")
    private String clientId;

    @NotBlank(message = "Request ID is required")
    private String requestId;

    @NotBlank(message = "Language is required")
    private String language;

    @NotBlank(message = "Time zone is required")
    private String timeZone;

    @Pattern(regexp = "\\d{14}", message = "Timestamp must be in the format 'yyyyMMddHHmmss'")
    private String timestamp;
}
