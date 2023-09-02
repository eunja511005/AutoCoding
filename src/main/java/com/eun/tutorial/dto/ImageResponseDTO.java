package com.eun.tutorial.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ImageResponseDTO {
    private String fileName;
    private byte[] fileContent;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String manager;
}
