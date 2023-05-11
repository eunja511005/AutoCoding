package com.eun.tutorial.controller.board;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.ZthhBoardDTO;
import com.eun.tutorial.service.ZthhBoardService;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private final ZthhBoardService zthhBoardService;
	
	@PostMapping("/saveForm")
	public ModelAndView init() {
		logger.debug("request url : /board/saveForm");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/board/boardSaveForm");
		
		return modelAndView;
	}
	
	@PostMapping("/save")
	public @ResponseBody Map<String, Object> getUserProfile(Authentication authentication, ZthhBoardDTO zthhBoardDTO){
    	logger.debug("request url : /join");
    	
    	PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
    	
    	zthhBoardDTO.setCreateId(userDetailsImpl.getUsername());
    	zthhBoardDTO.setUpdateId(userDetailsImpl.getUsername());
    	
    	zthhBoardService.save(zthhBoardDTO);
    	
    	Map<String, Object> res = new HashMap<>();
    	
    	res.put("result", "upload success");
        res.put("redirectUrl", "/board/listForm");
		return res;
	}
	
	@GetMapping("/listForm")
	public ModelAndView listForm(){
		logger.debug("request url : /board/listForm");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/board/boardListForm");
		
		return modelAndView;
	}
	
	@PostMapping("/list")
	public @ResponseBody Map<String, Object> list(){
    	logger.debug("request url : /join");
    	
    	Map<String, Object> res = new HashMap<>();
    	
    	res.put("result", "upload success");
        res.put("boardList", zthhBoardService.findAll());
		return res;
	}
	
	@PostMapping("/list/{boardId}")
	public @ResponseBody Map<String, Object> list(Authentication authentication, @PathVariable String boardId){
    	logger.debug("request url : /join");
    	
    	PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
    	
    	Map<String, Object> res = zthhBoardService.findById(boardId, userDetailsImpl);
    	
		return res;
	}
	
	@DeleteMapping("/delete/{id}")
	public @ResponseBody Map<String, Object> delete(Authentication authentication, @PathVariable String id){
    	logger.debug("request url : /join");
    	
    	PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
    	
    	Map<String, Object> res = zthhBoardService.delete(id, userDetailsImpl);
    	
		return res;
	}
	
    @PutMapping("/update")
    public @ResponseBody Map<String, Object> updateContent(Authentication authentication, @RequestBody ZthhBoardDTO zthhBoardDTO){

        log.info(zthhBoardDTO.toString());
        PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
    	zthhBoardDTO.setCreateId(userDetailsImpl.getUsername());
    	zthhBoardDTO.setUpdateId(userDetailsImpl.getUsername());

        zthhBoardService.update(zthhBoardDTO);
        
    	Map<String, Object> res = new HashMap<>();
    	
    	res.put("result", "update success");
        res.put("redirectUrl", "/board/listForm");
		return res;
    }
}
