package com.eun.tutorial.controller.main;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import com.eun.tutorial.dto.main.MenuDTO;
import com.eun.tutorial.service.main.MenuService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/menu")
@Slf4j
@AllArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/menu");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody List<MenuDTO> getMenuList() {
		return menuService.getMenuList();
	}

	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id) {
		log.info("Post List by ID : {}", id);
		MenuDTO menuDTOList = menuService.getMenuListById(id);
		return new ApiResponse<>(true, "Successfully retrieved the menu list.", menuDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse saveMenu(@RequestBody MenuDTO menuDTO) {
		menuService.saveMenu(menuDTO);
		return new ApiResponse<>(true, "Success message", null);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ApiResponse deleteMenu(@PathVariable String id) {
		log.info("Delete by ID : {}", id);
		menuService.deleteMenu(id);
		return new ApiResponse<>(true, "Successfully deleted the menu data.", null);
	}
	
    @GetMapping("/loadMenu")
    public ResponseEntity<String> loadMenu() {
        String menuHtml = menuService.generateMenuHtml();
        
        return ResponseEntity.ok().body(menuHtml);
    }
}
