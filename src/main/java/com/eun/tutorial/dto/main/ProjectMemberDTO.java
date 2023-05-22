package com.eun.tutorial.dto.main;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberDTO {
	private String name;
	private String email;
	private String contact;
	private String position;
	private String picture;
	private String introduction;
	private String id;
	private boolean delYn;
	private String createId;
	private LocalDateTime createDt;
	private String updateId;
	private LocalDateTime updateDt;
}
