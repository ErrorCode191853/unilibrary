package com.khoi.unilibrary.repository;

import com.khoi.unilibrary.entity.Book;

import java.util.List;

public interface CustomBookRepository {
    List<Book> findBorrowedBooksByUserId(Long userId);
}
