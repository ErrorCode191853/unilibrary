package com.khoi.unilibrary.service;

import com.khoi.unilibrary.dto.CategoryPayload;
import com.khoi.unilibrary.model.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Category createCategory(CategoryPayload categoryPayload);

    Page<Category> getAllCategories(String keyword, int page, int size, String[] sort);

    void deleteCategoryById(Integer id);

    Category editCategory(Integer id, CategoryPayload categoryPayload);
}
