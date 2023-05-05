package com.eun.tutorial.service;

import java.util.Collection;

import com.eun.tutorial.dto.Book;

public interface BookService {
    Collection<Book> getBooks();
    Book addBook(Book book);
}
