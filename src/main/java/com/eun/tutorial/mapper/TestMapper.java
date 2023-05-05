package com.eun.tutorial.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.Book;
import com.eun.tutorial.dto.UserInfoDTO;

@Mapper
public interface TestMapper {
    List<Book> getBookLists();
    UserInfoDTO getUser(Map<String, Object> map);
}