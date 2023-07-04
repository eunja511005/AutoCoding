package com.eun.tutorial.util;

import java.util.List;

import com.eun.tutorial.dto.main.DataTableRequestDTO;
import com.eun.tutorial.dto.main.DataTableResult;

public class DataTableUtil {
    public static <T> DataTableResult<T> getResult(DataTableRequestDTO searchDTO, List<T> data, int totalCount) {
        DataTableResult<T> result = new DataTableResult<>();
        int start = searchDTO.getStart();
        int length = searchDTO.getLength();
        int end = start + length;

        int pageNum = (start / length) + 1;
        int totalPages = (int) Math.ceil((double) totalCount / length);
        result.setDraw(searchDTO.getDraw());
        result.setPageLength(length);
        result.setRecordsFiltered(totalCount);
        result.setStart(start);
        result.setEnd(end);
        result.setRecordsTotal(totalCount);
        result.setPage(pageNum);
        result.setPageTotal(totalPages);
        result.setData(data);
        return result;
    }
}
