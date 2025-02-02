package api.moneyflow.user.service.impl;

import api.moneyflow.commom.exception.UserAlreadyExistsException;
import api.moneyflow.commom.exception.UserNotFoundException;
import api.moneyflow.user.controller.converters.UserConverter;
import api.moneyflow.user.payload.UserRequestPayload;
import api.moneyflow.user.payload.UserResponsePayload;
import api.moneyflow.user.repository.UserRepository;
import api.moneyflow.user.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponsePayload create(UserRequestPayload payload) {
        if (repository.existsByEmail(payload.email()) ) throw new UserAlreadyExistsException(payload.email());

        var user = UserConverter.toEntity(payload);
        user.setPassword(passwordEncoder.encode(payload.password()));

        repository.save(user);
        return UserConverter.toDto(user);
    }

    @Override
    public UserResponsePayload getByEmail(String email) {
        var user = repository.findByEmail(email);

        if (user == null) throw new UserNotFoundException(email);

        return UserConverter.toDto(user);
    }

    @Override
    public List<UserResponsePayload> getAll() {
        return repository.findAll().stream()
                .map(UserConverter::toDto)
                .toList();
    }

    @Override
    public UserResponsePayload update(UUID userId, UserRequestPayload payload) {
        return repository.findById(userId).map(user -> {
            var updatedUser = UserConverter.updateFromUser(user, payload);
            var savedUser = repository.save(updatedUser);

            return UserConverter.toDto(savedUser);
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public UserResponsePayload getById(UUID userId) {
        return repository.findById(userId)
                .map(UserConverter::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
