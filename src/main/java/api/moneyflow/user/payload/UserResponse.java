package api.moneyflow.user.payload;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email
) {
}
