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

package com.eun.tutorial.controller.external.pubdata;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.external.pubdata.RealEstatePriceItem.ItemsDTO;
import com.eun.tutorial.dto.external.pubdata.RealEstatePriceItem.RealEstatePriceItemDTO;
import com.eun.tutorial.dto.external.pubdata.TourismStayingRootDTO.RowDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableResult;
import com.eun.tutorial.service.external.pubdata.RealEstatePriceService;
import com.eun.tutorial.service.external.pubdata.TourismStayingService;
import com.eun.tutorial.util.DataTableUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/tourismStaying")
@Slf4j
@AllArgsConstructor
public class TourismStayingController {

	private final TourismStayingService tourPriceService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/external/pubdata/tourismStaying");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody DataTableResult<RowDTO> searchRealEstatePrice(@RequestBody DataTableRequestDTO searchDTO) throws JsonProcessingException, URISyntaxException {
	    return tourPriceService.searchTourismStaying(searchDTO);
	}

}
