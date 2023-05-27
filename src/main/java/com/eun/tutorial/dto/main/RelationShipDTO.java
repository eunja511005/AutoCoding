package com.eun.tutorial.dto.main;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationShipDTO {
	private int relation;
	private String fromUser;
	private String toUser;
	private String id;
	private boolean delYn;
	private String createId;
	private LocalDateTime createDt;
	private String updateId;
	private LocalDateTime updateDt;
}
