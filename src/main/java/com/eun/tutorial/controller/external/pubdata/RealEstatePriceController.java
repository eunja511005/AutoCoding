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

import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableResult;
import com.eun.tutorial.dto.main.RealEstatePriceItem.RealEstatePriceItemDTO;
import com.eun.tutorial.service.external.pubdata.RealEstatePriceService;
import com.eun.tutorial.util.DataTableUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/realEstatePrice")
@Slf4j
@AllArgsConstructor
public class RealEstatePriceController {

	private final RealEstatePriceService realEstatePriceService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/external/pubdata/realEstatePrice");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody DataTableResult<RealEstatePriceItemDTO> searchRealEstatePrice(@RequestBody DataTableRequestDTO searchDTO) throws JsonProcessingException, URISyntaxException {
	    List<RealEstatePriceItemDTO> itemList = realEstatePriceService.searchRealEstatePrice(searchDTO);
	    
	    int size = 0;
	    if(itemList!=null){
	    	size = itemList.size();
	    }
	    return DataTableUtil.getResult(searchDTO, itemList, size);
	}

//	@PostMapping("/list/{id}")
//	public @ResponseBody ApiResponse list(@PathVariable String id) {
//		log.info("Post List by ID : {}", id);
//		ApiMasterDTO apiMasterDTOList = apiMasterService.getApiMasterListById(id);
//		return new ApiResponse<>(true, "Successfully retrieved the apiMaster list.", apiMasterDTOList);
//	}
//
//	@PostMapping("/save")
//	public @ResponseBody ApiResponse saveApiMaster(@RequestBody ApiMasterDTO apiMasterDTO) {
//		if (StringUtils.isBlank(apiMasterDTO.getId())) {
//			apiMasterService.saveApiMaster(apiMasterDTO);
//			return new ApiResponse<>(true, "Success save", null);
//		} else {
//			apiMasterService.updateApiMaster(apiMasterDTO);
//			return new ApiResponse<>(true, "Success update", null);
//		}
//	}
//
//	@DeleteMapping("/{id}")
//	public @ResponseBody ApiResponse deleteApiMaster(@PathVariable String id) {
//		log.info("Delete by ID : {}", id);
//		apiMasterService.deleteApiMaster(id);
//		return new ApiResponse<>(true, "Successfully deleted the apiMaster data.", null);
//	}

}
