package com.khoi.unilibrary.repository;

import com.khoi.unilibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
