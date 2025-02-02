package api.moneyflow.commom.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super(CustomMessageError.USER_NOT_FOUND.getMessage() + email);
    }

    public UserNotFoundException(UUID userId) {
        super(CustomMessageError.USER_NOT_FOUND.getMessage() + userId);
    }
}
