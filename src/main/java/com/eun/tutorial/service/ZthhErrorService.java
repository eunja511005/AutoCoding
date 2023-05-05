package com.eun.tutorial.service;

import java.util.List;

import com.eun.tutorial.dto.ZthhErrorDTO;

public interface ZthhErrorService {
    void save(ZthhErrorDTO zthmError);
    List<ZthhErrorDTO> getErrorList();
	int delete(int deleteDay);
}