package com.khoi.unilibrary.service;

import com.khoi.unilibrary.dto.AuthorPayload;
import com.khoi.unilibrary.model.Author;
import org.springframework.data.domain.Page;

public interface AuthorService {
    Author createAuthor(AuthorPayload authorPayload);

    Page<Author> getAllAuthors(String keyword, int page, int size, String[] sort);

    void deleteAuthorById(Integer id);

    Author editAuthor(Integer id, AuthorPayload authorPayload);
}
