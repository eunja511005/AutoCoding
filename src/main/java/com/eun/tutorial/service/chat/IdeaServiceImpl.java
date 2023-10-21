package com.eun.tutorial.service.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.dto.chat.ChatMessage;
import com.eun.tutorial.dto.chat.IdeaDTO;
import com.eun.tutorial.dto.chat.IdeaListRequest;
import com.eun.tutorial.mapper.ZthhFileAttachMapper;
import com.eun.tutorial.mapper.chat.IdeaMapper;
import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class IdeaServiceImpl implements IdeaService{
	private final IdeaMapper ideaMapper;
	private final ZthhFileAttachMapper zthhFileAttachMapper;
	
	@Override
	public List<IdeaDTO> getIdeaList(IdeaListRequest ideaListRequest) {
		int offset = (ideaListRequest.getPage() - 1) * ideaListRequest.getSize();
		List<IdeaDTO> resultList = ideaMapper.selectIdeaList(offset, ideaListRequest.getSize());
		for (IdeaDTO ideaDTO : resultList) {
			if(ideaDTO.getAttachId() != null) {
				List<String> imageUrlList = new ArrayList<>();
				List<ZthhFileAttachDTO> zthhFileAttachDTOList = zthhFileAttachMapper.selectFileListByAttachId(ideaDTO.getAttachId());
				for (ZthhFileAttachDTO zthhFileAttachDTO : zthhFileAttachDTOList) {
					imageUrlList.add(zthhFileAttachDTO.getFileName());
				}
				
				ideaDTO.setImageUrlList(imageUrlList);
			}
		}
		
		return resultList;
		
	}

	@Override
	public int saveIdea(IdeaDTO ideaDTO) {
		if (StringUtils.isBlank(ideaDTO.getId())) {
			ideaDTO.setId("IDEA_"+UUID.randomUUID());
			return ideaMapper.insertIdea(ideaDTO);
		} else {
			return ideaMapper.updateIdea(ideaDTO);
		}
	}

	@Override
	public int updateIdea(IdeaDTO ideaDTO) {
		return ideaMapper.updateIdea(ideaDTO);
	}

	@Override
	public int deleteIdea(String id, String username) {
		
		if(username.equals(getIdeaById(id).getCreateId())){
			return ideaMapper.deleteIdea(id);
		}
		
		return 0;
				
	}

	@Override
	public IdeaDTO getIdeaById(String id) {
		return ideaMapper.getIdeaById(id);
	}

	@Override
	public long getTotalIdeas() {
		return ideaMapper.selectTotalIdeas();
	}

}
