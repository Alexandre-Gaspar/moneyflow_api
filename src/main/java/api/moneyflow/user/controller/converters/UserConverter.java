package api.moneyflow.user.controller.converters;

import api.moneyflow.user.User;
import api.moneyflow.user.payload.UserRequest;
import api.moneyflow.user.payload.UserResponse;

public class UserConverter {
    private UserConverter() {}

    public static User toEntity(UserRequest payload) {
        User newUser = new User();
        newUser.setName(payload.name());
        newUser.setEmail(payload.email());
        newUser.setPassword(payload.password());
        return newUser;
    }

    public static User fromResponseToEntity(UserResponse payload) {
        User newUser = new User();
        newUser.setId(payload.id());
        newUser.setName(payload.name());
        newUser.setEmail(payload.email());
        return newUser;
    }

    public static UserResponse toDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User updateFromUser(User fetchedUser, UserRequest payload) {
        fetchedUser.setName(payload.name());
        fetchedUser.setEmail(payload.email());

        return fetchedUser;
    }
}
