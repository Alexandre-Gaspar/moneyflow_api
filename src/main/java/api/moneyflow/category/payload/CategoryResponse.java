package api.moneyflow.category.payload;

import java.math.BigDecimal;

public record CategoryResponse(
        Long id,
        String name,
        BigDecimal spendingLimit
) {
}
