package api.moneyflow.expense.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenseResponse(
        UUID id,
        BigDecimal amount,
        String description,
        UUID userId,
        Long categoryID,
        LocalDateTime date
){
}