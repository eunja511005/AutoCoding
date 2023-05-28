package com.eun.tutorial.service;

import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.dto.main.ErrorHistDTO;
import com.eun.tutorial.service.main.ErrorHistService;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ZthhErrorServiceImpl implements ZthhErrorService {

	//private final CommonMapper commonDao;
	private final ErrorHistService errorHistService;

    @Override
    public void save(ZthhErrorDTO zthmError) {
    	ErrorHistDTO errorHistDTO = new ErrorHistDTO();
    	
    	
    	if(zthmError.getErrorMessage().length()>2000) {
    		errorHistDTO.setErrorMsg(zthmError.getErrorMessage().substring(0, 2000));
    		//zthmError.setErrorMessage(zthmError.getErrorMessage().substring(0, 2000));
    	}else {
    		errorHistDTO.setErrorMsg(zthmError.getErrorMessage());
    	}
    	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			errorHistDTO.setCreateId("anonymous");
		}else {
			PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
			errorHistDTO.setCreateId(userDetailsImpl.getName());
		}
    	
    	try {//오류 발생해도 기존 로직에는 영향을 주지 않고 오류 로그만 남김
    		errorHistService.saveErrorHist(errorHistDTO);
    	}catch (Exception e) {
    		log.error(e.getMessage());
		}
    }

    @Override
    public List<ZthhErrorDTO> getErrorList() {
        return null;
    }

	@Override
	public int delete(int deleteDay) {
		return 0;
	}
}