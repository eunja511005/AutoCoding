package com.eun.tutorial.controller.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.RelationShipDTO;
import com.eun.tutorial.service.main.RelationShipService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/relationShip")
@Slf4j
@AllArgsConstructor
public class RelationShipController {

	private final RelationShipService relationShipService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/relationShip");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody List<RelationShipDTO> getRelationShipList() {
		return relationShipService.getRelationShipList();
	}

	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id) {
		log.info("Post List by ID : {}", id);
		RelationShipDTO relationShipDTOList = relationShipService.getRelationShipListById(id);
		return new ApiResponse<>(true, "Successfully retrieved the relationShip list.", relationShipDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse saveRelationShip(@RequestBody RelationShipDTO relationShipDTO) {
		relationShipService.saveRelationShip(relationShipDTO);
		return new ApiResponse<>(true, "Success message", null);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ApiResponse deleteRelationShip(@PathVariable String id) {
		log.info("Delete by ID : {}", id);
		relationShipService.deleteRelationShip(id);
		return new ApiResponse<>(true, "Successfully deleted the relationShip data.", null);
	}

}
