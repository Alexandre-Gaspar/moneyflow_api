package api.moneyflow.expense.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseRequest (
        @Positive
        @NotNull(message = "Value is required")
        BigDecimal amount,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "User id is required")
        UUID userId,

        @NotNull(message = "Category id is required")
        Long categoryId
){
}
