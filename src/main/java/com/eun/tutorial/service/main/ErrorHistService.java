/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the developer.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The developer shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

package com.eun.tutorial.service.main;

import java.util.List;

import com.eun.tutorial.dto.main.ErrorHistDTO;

public interface ErrorHistService {
	List<ErrorHistDTO> getErrorHistList();
	int saveErrorHist(ErrorHistDTO errorHistDTO);
	int updateErrorHist(ErrorHistDTO errorHistDTO);
	int deleteErrorHist(String id);
	ErrorHistDTO getErrorHistListById(String id);
}