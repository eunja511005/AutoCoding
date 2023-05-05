package com.eun.tutorial.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.mapper.CommonMapper;
import com.eun.tutorial.mapper.ZthhFileAttachMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ZthhFileAttachServiceImpl implements ZthhFileAttachService {

	private final ZthhFileAttachMapper zthhFileAttachMapper;
	private static final Logger logger = LoggerFactory.getLogger(ZthhFileAttachServiceImpl.class);

    @Override
    public int save(ZthhFileAttachDTO zthhFileAttachDTO) {
    	return zthhFileAttachMapper.save(zthhFileAttachDTO);
    }

    @Override
    public List<ZthhFileAttachDTO> findAll() {
        return zthhFileAttachMapper.findAll();
    }

	@Override
	public ZthhFileAttachDTO getOne(Map<String, Object> map) {
		return zthhFileAttachMapper.getOne(map);
	}
}