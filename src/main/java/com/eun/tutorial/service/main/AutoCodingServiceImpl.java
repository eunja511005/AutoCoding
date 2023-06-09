package com.eun.tutorial.service.main;

import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.main.AutocodingFieldDTO;
import com.eun.tutorial.mapper.main.AutoCodingMapper;
import com.eun.tutorial.service.user.PrincipalDetails;
import com.eun.tutorial.util.AuthUtils;
import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutoCodingServiceImpl implements AutoCodingService {
	private final AutoCodingMapper autoCodingMapper;
	
	@Override
	public List<AutocodingFieldDTO> getAutoCodingList(){
		return autoCodingMapper.selectAutoCoding();
	}

	@Override
	public int saveAutoCoding(AutocodingFieldDTO autocodingFieldDTO) {
		String loginUser = AuthUtils.getLoginUser();
		autocodingFieldDTO.setCreateId(loginUser);
		autocodingFieldDTO.setUpdateId(loginUser);
		
        if (StringUtils.isBlank(autocodingFieldDTO.getId())) {
        	autocodingFieldDTO.setId("autocoding_"+UUID.randomUUID());
            return autoCodingMapper.insertAutoCoding(autocodingFieldDTO);
        } else {
        	return autoCodingMapper.updateAutoCoding(autocodingFieldDTO);
        }
	}

	@Override
	public int deleteAutoCoding(String id) {		
		return autoCodingMapper.deleteAutoCoding(id);
	}

	@Override
	public AutocodingFieldDTO getAutoCodingListById(String id) {
		return autoCodingMapper.getAutoCodingListById(id);
	}
}
