package fpt.he190091.assignment01.dtos;

import fpt.he190091.assignment01.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryRequest {
    private String categoryName;
    private String categoryDescription;
    private Boolean isActive;
    private Long parentCategoryId;
}
