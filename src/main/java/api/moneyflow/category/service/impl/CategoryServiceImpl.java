package api.moneyflow.category.service.impl;

import api.moneyflow.category.converters.CategoryConverter;
import api.moneyflow.category.payload.CategoryRequest;
import api.moneyflow.category.payload.CategoryResponse;
import api.moneyflow.category.repository.CategoryRepository;
import api.moneyflow.category.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        var category = CategoryConverter.toEntity(request);
        repository.save(category);

        return CategoryConverter.toDto(category);
    }

    @Override
    public CategoryResponse update(Long categoryId, CategoryRequest request) {
        return repository.findById(categoryId)
                .map(category -> {
                    var categoryRaw = CategoryConverter.updateFromCategory(category, request);

                    var updatedCategory = repository.save(categoryRaw);

                    return CategoryConverter.toDto(updatedCategory);
                }).orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada com o id: " + categoryId));
    }

    @Override
    public List<CategoryResponse> getAll() {
        return repository.findAll().stream()
                .map(CategoryConverter::toDto)
                .toList();
    }

    @Override
    public CategoryResponse getByName(String name) {
        return repository.findByName(name)
                .map(CategoryConverter::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada com o nome: " + name));
    }

    @Override
    public CategoryResponse getById(Long categoryId) {
        return repository.findById(categoryId)
                .map(CategoryConverter::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada com o id: " + categoryId));
    }
}
