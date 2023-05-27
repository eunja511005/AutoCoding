package com.eun.tutorial.service.main;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.main.RelationShipDTO;
import com.eun.tutorial.mapper.main.RelationShipMapper;

import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RelationShipServiceImpl implements RelationShipService {

	private final RelationShipMapper relationShipMapper;

	@Override
	public List<RelationShipDTO> getRelationShipList() {
		return relationShipMapper.selectRelationShipList();
	}

	@Override
	public int saveRelationShip(RelationShipDTO relationShipDTO) {
		if (StringUtils.isBlank(relationShipDTO.getId())) {
			relationShipDTO.setId("relationShip_"+UUID.randomUUID());
			return relationShipMapper.insertRelationShip(relationShipDTO);
		} else {
			return relationShipMapper.updateRelationShip(relationShipDTO);
		}
	}

	@Override
	public int deleteRelationShip(String id) {
		return relationShipMapper.deleteRelationShip(id);
	}

	@Override
	public RelationShipDTO getRelationShipListById(String id) {
		return relationShipMapper.getRelationShipListById(id);
	}

}