package com.khoi.unilibrary.repository;

import com.khoi.unilibrary.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Page<Author> findAll(Pageable pageable);

    Page<Author> findByFirstNameContainingOrLastNameContainingAllIgnoreCase(String firstName, String lastName, Pageable pageable);

    @Query(value = "SELECT * FROM author ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Author getOneRandom();
}
