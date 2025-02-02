package api.moneyflow.commom.exception;

import java.util.UUID;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super(CustomMessageError.USER_ALREADY_EXISTS.getMessage() + email);
    }

    public UserAlreadyExistsException(UUID id) {
        super(CustomMessageError.USER_ALREADY_EXISTS.getMessage() + id);
    }
}
