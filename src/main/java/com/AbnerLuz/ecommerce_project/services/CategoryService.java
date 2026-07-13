package com.AbnerLuz.ecommerce_project.services;

import com.AbnerLuz.ecommerce_project.core.Categories;
import com.AbnerLuz.ecommerce_project.dtos.CategoryRequest;
import com.AbnerLuz.ecommerce_project.dtos.CategoryResponse;
import com.AbnerLuz.ecommerce_project.exceptions.ResourceNotFoundException;
import com.AbnerLuz.ecommerce_project.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse toResponse(Categories category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public CategoryResponse createCategory(CategoryRequest data) {
        Categories category = new Categories(data.name(), data.description());
        return toResponse(categoryRepository.save(category));
    }

    public CategoryResponse findCategoryById(Long id) {
        Categories category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
        return toResponse(category);
    }

    public List<CategoryResponse> listAllCategories() {
        return categoryRepository.findAll().stream().map(
                this::toResponse).toList();
    }

    public void deleteCategoryById(Long id) {
        if (!categoryRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
        categoryRepository.deleteById(id);
    }
}
