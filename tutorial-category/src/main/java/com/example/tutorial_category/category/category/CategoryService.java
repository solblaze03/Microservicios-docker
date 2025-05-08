package com.example.tutorial_category.category.category;

import java.util.List;

public interface CategoryService {

    Category get(Long id);

    List<Category> findAll();

    void save(Long id, CategoryDto dto);

    void delete(Long id) throws Exception;
}
