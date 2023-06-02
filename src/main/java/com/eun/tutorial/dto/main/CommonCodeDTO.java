package com.eun.tutorial.dto.main;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommonCodeDTO {
	  private Long id;
	  private String codeGroup;
	  private String code;
	  private int codeOrder;
	  private String value;
	  private LocalDateTime createdAt;
	  private LocalDateTime updatedAt;
	}