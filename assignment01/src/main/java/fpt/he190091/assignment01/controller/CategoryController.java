package fpt.he190091.assignment01.controller;

import fpt.he190091.assignment01.dtos.CategoryRequest;
import fpt.he190091.assignment01.dtos.CategoryResponse;
import fpt.he190091.assignment01.entity.Category;
import fpt.he190091.assignment01.mapper.CategoryMapper;
import fpt.he190091.assignment01.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return mapper.toResponseList(categoryService.findAllCategory());
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return mapper.toResponse(categoryService.findById(id));
    }

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest category){
        return mapper.toResponse(categoryService.createCategory(category));
    }

    @PatchMapping("update/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest category){
        return mapper.toResponse(categoryService.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
