package api.moneyflow.category.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CategoryRequest(
        @NotBlank(message = "Name is required")
        String name,

        @Positive
        BigDecimal spendingLimit
) {
}
