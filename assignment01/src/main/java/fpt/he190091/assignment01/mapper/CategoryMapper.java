package fpt.he190091.assignment01.mapper;

import fpt.he190091.assignment01.dtos.CategoryResponse;
import fpt.he190091.assignment01.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        if (category == null) return null;

        CategoryResponse res = new CategoryResponse();
        res.setCategoryId(category.getCategoryID());
        res.setCategoryName(category.getCategoryName());
        res.setCategoryDescription(category.getCategoryDescription());
        res.setIsActive(category.getIsActive());

        // parent
        if (category.getParentCategory() != null) {
            res.setParentCategoryId(category.getParentCategory().getCategoryID());
        }

        // children
        if (category.getSubCategories() != null) {
            res.setSubCategoriesId(
                    category.getSubCategories()
                            .stream()
                            .map(Category::getCategoryID)
                            .toList()
            );
        } else {
            res.setSubCategoriesId(List.of());
        }

        return res;
    }

    public List<CategoryResponse> toResponseList(List<Category> categories) {
        return categories.stream()
                .map(this::toResponse)
                .toList();
    }
}