package api.moneyflow.user.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UserRequestPayload(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @Positive(message = "Balance must be positive")
        BigDecimal balance
) {
}
