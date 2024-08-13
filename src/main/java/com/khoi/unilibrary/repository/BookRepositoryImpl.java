package com.khoi.unilibrary.repository;

import com.khoi.unilibrary.entity.Book;

import java.util.List;

public class BookRepositoryImpl implements CustomBookRepository {

    @Override
    public List<Book> findBorrowedBooksByUserId(Long userId) {
        return List.of();
    }
}
