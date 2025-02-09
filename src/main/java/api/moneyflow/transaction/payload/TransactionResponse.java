package api.moneyflow.transaction.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        BigDecimal amount,
        String description,
        UUID userId,
        //Long categoryID,
        String type,
        LocalDateTime date
){
}