package fpt.he190091.assignment01.service;

import fpt.he190091.assignment01.dtos.CategoryRequest;
import fpt.he190091.assignment01.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> findAllCategory();

    Category findById(Long id);

    Category createCategory(CategoryRequest category);

    void deleteCategory(Long id);

    Category updateCategory(Long id, CategoryRequest dto);
}
