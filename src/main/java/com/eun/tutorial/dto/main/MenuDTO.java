package com.eun.tutorial.dto.main;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
	private String category;
	private int menuLevel;
	private String menuAuth;
	private String menuId;
	private String menuPath;
	private String menuIcon;
	private int menuOrder;
	private String parentMenuId;
	private String id;
	private boolean delYn;
	private String createId;
	private LocalDateTime createDt;
	private String updateId;
	private LocalDateTime updateDt;
}
