package com.eun.tutorial.api.v1.dto.stock;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockHeader {
	@NotBlank(message = "Mode is required")
	private String mode; // L : local stock, B : back-end stock

    private String reservationNo;
}
