package api.moneyflow.category.converters;

import api.moneyflow.category.Category;
import api.moneyflow.category.payload.CategoryRequest;
import api.moneyflow.category.payload.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public static Category toEntity(CategoryRequest request) {
        var category = new Category();
        category.setName(request.name());

        return category;
    }

    public static Category fromResponseToEntity(CategoryResponse response) {
        var category = new Category();
        category.setId(response.id());
        category.setName(response.name());

        return category;
    }

    public static CategoryResponse toDto(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }

    public static Category updateFromCategory(Category category, CategoryRequest request) {
        category.setName(request.name());
        return category;
    }
}
