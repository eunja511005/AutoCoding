package com.eun.tutorial.api.v1.dto.stock;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockItem {
	
    @NotBlank(message = "Item category is required")
    @Size(max = 100, message = "Item category must not exceed 100 characters")
	private String itemCategory;  // 유형(모바일, 가전), 무형(라이선스, 보험, 서비스, 교육, 유지보수 등)

    @Min(value = 1, message = "Item number must be greater than 0")
    private int itemNumber;

    @NotBlank(message = "Model code is required")
    @Size(max = 100, message = "Model code must not exceed 100 characters")
    private String modelCode;

    @NotBlank(message = "Plant is required")
    @Size(max = 50, message = "Plant must not exceed 50 characters")
    private String plant;

    @NotBlank(message = "Storage location is required")
    @Size(max = 50, message = "Storage location must not exceed 50 characters")
    private String storageLocation;

    @Size(max = 50, message = "Valuation type must not exceed 50 characters")
    private String valuationType;

    @Size(max = 100, message = "Vendor code must not exceed 100 characters")
    private String vendorCode;
	
	@Min(value = 0, message = "Confirm quantity cannot be negative")
	private int requestQuantity;
	
	@Pattern(regexp = "\\d{14}", message = "Request date must be in the format 'yyyyMMddHHmmss'")
	private String requestDate;
	
    @Min(value = 0, message = "Confirm quantity cannot be negative")
    private int confirmQuantity;
	
    @Pattern(regexp = "\\d{14}", message = "Request date must be in the format 'yyyyMMddHHmmss'")
    private String confirmDate;
}
