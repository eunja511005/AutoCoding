package com.eun.tutorial.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.Book;
import com.eun.tutorial.exception.DuplicateBookException;
import com.eun.tutorial.mapper.TestMapper;
import com.eun.tutorial.util.StringUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
	private final TestMapper testDao;

	@Override
	public Collection<Book> getBooks() {
		return testDao.getBookLists();
	}

	@Override
	public Book addBook(Book book) {
		if (StringUtils.isBlank(book.getIsbn())) {
			throw new DuplicateBookException(book);
		}
		return null;
	}

}
