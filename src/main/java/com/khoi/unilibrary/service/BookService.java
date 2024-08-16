package com.khoi.unilibrary.service;

import com.khoi.unilibrary.dto.BookPayload;
import com.khoi.unilibrary.model.Book;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface BookService {
    Book createBook(BookPayload bookPayload) throws ParseException;

    Page<Book> getAllBooks(String keyword, String statusName, int page, int size);

    void deleteBookById(Integer id);

    Book editBook(Integer id, BookPayload bookPayload) throws ParseException;

}
