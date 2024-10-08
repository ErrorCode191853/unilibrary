package com.khoi.unilibrary.repository;

import com.khoi.unilibrary.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findAll(Pageable pageable);

    Category findByNameIgnoreCase(String name);

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = "SELECT * FROM category ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Category getOneRandom();
}
