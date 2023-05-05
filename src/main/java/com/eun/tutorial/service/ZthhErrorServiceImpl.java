package com.eun.tutorial.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.mapper.CommonMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ZthhErrorServiceImpl implements ZthhErrorService {

	private final CommonMapper commonDao;
	private static final Logger logger = LoggerFactory.getLogger(ZthhErrorServiceImpl.class);

    @Override
    public void save(ZthhErrorDTO zthmError) {
    	commonDao.save(zthmError);
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