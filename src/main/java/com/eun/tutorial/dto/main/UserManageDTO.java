/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the @autoCoding.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The @autoCoding shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

package com.eun.tutorial.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManageDTO {
	private String username;
	private String password;
	private String email;
	private byte[] salt;
	private String role;
	private String picture;
	private boolean isEnable;
    private String lastLoginDt;
	private String createId;
	private String createTime;
	private String updateId;
	private String updateTime;
}
