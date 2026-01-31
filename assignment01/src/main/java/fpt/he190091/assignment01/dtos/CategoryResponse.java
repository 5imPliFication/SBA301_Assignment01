package fpt.he190091.assignment01.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
    private Boolean isActive;
    private Long parentCategoryId;
    private List<Long> subCategoriesId;
}
