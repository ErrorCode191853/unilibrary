package com.khoi.unilibrary.repository;

import com.khoi.unilibrary.model.Category;
import com.khoi.unilibrary.model.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Integer> {
    Page<Work> findAll(Pageable pageable);

    Page<Work> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Work> findByCategoriesEquals(Category category, Pageable pageable);

    Page<Work> findByCategoriesEqualsAndTitleContainingIgnoreCase(Category category, String title, Pageable pageable);

    @Query(value = "SELECT * FROM work ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Work getOneRandom();
}
