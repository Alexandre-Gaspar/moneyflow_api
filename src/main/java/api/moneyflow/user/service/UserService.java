package api.moneyflow.user.service;

import api.moneyflow.user.payload.UserRequest;
import api.moneyflow.user.payload.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse create(UserRequest payload);

    UserResponse getByEmail(String email);

    List<UserResponse> getAll();

    UserResponse update(UUID userId, UserRequest payload);

    UserResponse getById(UUID userId);
}
