package api.moneyflow.category.controller;

import api.moneyflow.category.payload.CategoryRequest;
import api.moneyflow.category.payload.CategoryResponse;
import api.moneyflow.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> registerCategory(@RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(categoryId, request));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/name/{categoryName}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String categoryName) {
        return ResponseEntity.ok(categoryService.getByName(categoryName));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getById(categoryId));
    }
}
