package com.eun.tutorial.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.mapper.CommonMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ZthhErrorServiceImpl implements ZthhErrorService {

	private final CommonMapper commonDao;
	private static final Logger logger = LoggerFactory.getLogger(ZthhErrorServiceImpl.class);

    @Override
    public void save(ZthhErrorDTO zthmError) {
    	if(zthmError.getErrorMessage().length()>2000) {
    		zthmError.setErrorMessage(zthmError.getErrorMessage().substring(0, 2000));
    	}
    	
    	try {//오류 발생해도 기존 로직에는 영향을 주지 않고 오류 로그만 남김
    		commonDao.save(zthmError);
    	}catch (Exception e) {
    		log.error(e.getMessage());
		}
    }

    @Override
    public List<ZthhErrorDTO> getErrorList() {
        return commonDao.findAll();
    }

	@Override
	public int delete(int deleteDay) {
		return commonDao.delete(deleteDay);
	}
}