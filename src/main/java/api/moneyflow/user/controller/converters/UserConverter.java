package api.moneyflow.user.controller.converters;

import api.moneyflow.user.User;
import api.moneyflow.user.payload.UserRequestPayload;
import api.moneyflow.user.payload.UserResponsePayload;

public class UserConverter {
    public static User toEntity(UserRequestPayload payload) {
        User newUser = new User();
        newUser.setName(payload.name());
        newUser.setEmail(payload.email());
        newUser.setPassword(payload.password());
        newUser.setBalance(payload.balance());
        return newUser;
    }

    public static User fromResponseToEntity(UserResponsePayload payload) {
        User newUser = new User();
        newUser.setId(payload.id());
        newUser.setName(payload.name());
        newUser.setEmail(payload.email());
        newUser.setBalance(payload.balance());
        return newUser;
    }

    public static UserResponsePayload toDto(User user) {
        return new UserResponsePayload(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBalance()
        );
    }

    public static User updateFromUser(User fetchedUser, UserRequestPayload payload) {
        fetchedUser.setName(payload.name());
        fetchedUser.setEmail(payload.email());
        fetchedUser.setBalance(payload.balance());

        return fetchedUser;
    }
}
