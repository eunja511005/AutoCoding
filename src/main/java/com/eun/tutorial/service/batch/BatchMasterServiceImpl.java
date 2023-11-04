package com.eun.tutorial.service.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.batch.BatchMasterDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.mapper.batch.ZthmBatchMasterMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class BatchMasterServiceImpl implements BatchMasterService {
	private final ZthmBatchMasterMapper zthmBatchMasterMapper;
	

	@Override
	public List<BatchMasterDTO> getBatchMasterList() {
		return zthmBatchMasterMapper.selectBatchMasterList();
	}


	@Override
	public int updateBatchMasterReady(BatchMasterDTO batchMasterDTO) {
		return zthmBatchMasterMapper.updateBatchMasterReady(batchMasterDTO);
	}
	
	@Override
	public int updateBatchMasterInprogress(BatchMasterDTO batchMasterDTO) {
		return zthmBatchMasterMapper.updateBatchMasterInprogress(batchMasterDTO);
	}


	@Override
	public List<BatchMasterDTO> searchBatchMasters(DataTableRequestDTO searchDTO) {
		Map<String, Object> params = new HashMap<>();
		params.put("offset", searchDTO.getStart());
		params.put("limit", searchDTO.getLength());
		params.put("orderColumnName", searchDTO.getOrderColumnName());
		params.put("orderDirection", searchDTO.getOrderDirection());
		params.put("search", searchDTO.getSearch());
		
		return zthmBatchMasterMapper.selectBatchMasters(params);
	}


	@Override
	public int getTotalCount(DataTableRequestDTO searchDTO) {
		Map<String, Object> params = new HashMap<>();
		params.put("search", searchDTO.getSearch());
		return zthmBatchMasterMapper.getTotalCount(params);
	}


	@Override
	public int saveBatchMaster(BatchMasterDTO batchMasterDTO) {
		batchMasterDTO.setId("BATCH_"+UUID.randomUUID());
		return zthmBatchMasterMapper.insertBatchMaster(batchMasterDTO);
	}


	@Override
	public int updateBatchMaster(BatchMasterDTO batchMasterDTO) {
		return zthmBatchMasterMapper.updateBatchMaster(batchMasterDTO);
	}


	@Override
	public Map<String, Integer> deleteBatchMaster(List<String> idList) {
		Map<String, Integer> resMap = new HashMap<>();
		
		int deleteCount = 0;
		for (String id : idList) {
			zthmBatchMasterMapper.deleteBatchMaster(id);
			deleteCount++;
		}
		
        resMap.put("deleteCount", deleteCount);
        
        return resMap;
	}


	@Override
	public BatchMasterDTO getBatchMasterById(String id) {
		return zthmBatchMasterMapper.getBatchMasterById(id);
	}


	@Override
	@Transactional
	public Map<String, Integer> saveBatchMaster(List<BatchMasterDTO> batchMasterDTOList) {
		Map<String, Integer> resMap = new HashMap<>();
		
		int inserCount = 0;
		int updateCount = 0;
        for (BatchMasterDTO batchMasterDTO : batchMasterDTOList) {
            if (zthmBatchMasterMapper.getBatchMasterById(batchMasterDTO.getId()) != null) {
            	zthmBatchMasterMapper.updateBatchMaster(batchMasterDTO);
            	updateCount++;  
            } else {
            	batchMasterDTO.setId("BATCH_"+UUID.randomUUID());
            	zthmBatchMasterMapper.insertBatchMaster(batchMasterDTO);
            	inserCount++;
            }
        }
        
        resMap.put("inserCount", inserCount);
        resMap.put("updateCount", updateCount);
        
        return resMap;
	}

}
