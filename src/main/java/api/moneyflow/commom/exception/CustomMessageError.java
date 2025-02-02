package api.moneyflow.commom.exception;

public enum CustomMessageError {
    USER_ALREADY_EXISTS("User already exists with: "),
    USER_NOT_FOUND("User not found with: ");

    private String message;

    CustomMessageError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
