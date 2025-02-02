package api.moneyflow.user.service;

import api.moneyflow.user.payload.UserRequestPayload;
import api.moneyflow.user.payload.UserResponsePayload;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponsePayload create(UserRequestPayload payload);

    UserResponsePayload getByEmail(String email);

    List<UserResponsePayload> getAll();

    UserResponsePayload update(UUID userId, UserRequestPayload payload);

    UserResponsePayload getById(UUID userId);
}
