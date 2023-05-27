package com.eun.tutorial.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.main.RelationShipDTO;

@Mapper
public interface RelationShipMapper {
	List<RelationShipDTO> selectRelationShipList();
	int insertRelationShip(RelationShipDTO relationShipDTO);
	int updateRelationShip(RelationShipDTO relationShipDTO);
	int deleteRelationShip(String id);
	RelationShipDTO getRelationShipListById(String id);
}