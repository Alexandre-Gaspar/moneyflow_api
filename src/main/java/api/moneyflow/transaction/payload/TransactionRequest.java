package api.moneyflow.transaction.payload;

import api.moneyflow.transaction.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(
        @Positive
        @NotNull(message = "Value is required")
        BigDecimal amount,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "User id is required")
        UUID userId,

        @NotNull(message = "Category id is required")
        Long categoryId,

        @NotNull(message = "Transaction type is required")
        TransactionType type
){
}
