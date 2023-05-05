package com.eun.tutorial.exception;

import com.eun.tutorial.dto.Book;

import lombok.Getter;

@Getter
public class DuplicateBookException extends RuntimeException {
    private final Book book;

    public DuplicateBookException(Book book) {
        this.book = book;
    }
}
