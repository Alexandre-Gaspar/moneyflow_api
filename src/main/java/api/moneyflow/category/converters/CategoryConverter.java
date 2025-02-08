package api.moneyflow.category.converters;

import api.moneyflow.category.Category;
import api.moneyflow.category.payload.CategoryRequest;
import api.moneyflow.category.payload.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    private CategoryConverter() {}

    public static Category toEntity(CategoryRequest request) {
        var category = new Category();
        category.setName(request.name());
        category.setSpendingLimit(request.spendingLimit());

        return category;
    }

    public static Category fromResponseToEntity(CategoryResponse response) {
        return new Category(
                response.id(),
                response.name(),
                response.spendingLimit()
        );
    }

    public static CategoryResponse toDto(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getSpendingLimit()
        );
    }

    public static Category updateFromCategory(Category category, CategoryRequest request) {
        category.setName(request.name());
        category.setSpendingLimit(request.spendingLimit());
        return category;
    }
}
