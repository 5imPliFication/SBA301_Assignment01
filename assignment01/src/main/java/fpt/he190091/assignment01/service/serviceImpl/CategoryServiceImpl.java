package fpt.he190091.assignment01.service.serviceImpl;

import fpt.he190091.assignment01.dtos.CategoryRequest;
import fpt.he190091.assignment01.entity.Category;
import fpt.he190091.assignment01.repository.CategoryRepository;
import fpt.he190091.assignment01.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    @Override
    public List<Category> findAllCategory() {
        return repo.findAll();
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Category createCategory(CategoryRequest dto) {
        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setCategoryDescription(dto.getCategoryDescription());
        category.setIsActive(dto.getIsActive());

        if (dto.getParentCategoryId() != null) {
            Category parent = repo.findById(dto.getParentCategoryId())
                    .orElse(null);
            category.setParentCategory(parent);
        }

        return repo.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Category updateCategory(Long id, CategoryRequest dto) {
        Category category = findById(id);
        if (category == null) return null;

        if (dto.getCategoryName() != null)
            category.setCategoryName(dto.getCategoryName());

        if (dto.getCategoryDescription() != null)
            category.setCategoryDescription(dto.getCategoryDescription());

        if (dto.getIsActive() != null)
            category.setIsActive(dto.getIsActive());

        if (dto.getParentCategoryId() != null)
            category.setParentCategory(findById(dto.getParentCategoryId()));

        return repo.save(category);
    }
}
