package com.eun.tutorial.service.main;

import java.util.List;

import com.eun.tutorial.dto.main.RelationShipDTO;

public interface RelationShipService {
	List<RelationShipDTO> getRelationShipList();
	int saveRelationShip(RelationShipDTO relationShipDTO);
	int deleteRelationShip(String id);
	RelationShipDTO getRelationShipListById(String id);
}