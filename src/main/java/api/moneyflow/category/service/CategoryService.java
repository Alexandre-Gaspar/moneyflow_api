package api.moneyflow.category.service;

import api.moneyflow.category.Category;
import api.moneyflow.category.payload.CategoryRequest;
import api.moneyflow.category.payload.CategoryResponse;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long categoryId, CategoryRequest request);

    List<CategoryResponse> getAll();

    CategoryResponse getByName(String name);

    CategoryResponse getById(Long categoryId);
}
