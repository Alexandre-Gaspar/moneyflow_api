package api.moneyflow.user;

import api.moneyflow.commom.exception.UserAlreadyExistsException;
import api.moneyflow.commom.exception.UserNotFoundException;
import api.moneyflow.user.payload.UserRequest;
import api.moneyflow.user.repository.UserRepository;
import api.moneyflow.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Nested
    @DisplayName("Create user")
    class Create {

        @Test
        @DisplayName("Should create a user successfully")
        void shouldCreateUserSuccessfully() {
            var user = new User(
                    UUID.randomUUID(),
                    "Joe Doe",
                    "joe@doe",
                    passwordEncoder.encode("123456")
            );

            doReturn(user).when(userRepository).save(userCaptor.capture());
            var request = new UserRequest(
                    "Joe Doe",
                    "joe@doe",
                    "123456"
            );

            var response = userService.create(request);

            assertNotNull(response);
            assertEquals(request.name(), userCaptor.getValue().getName());
            assertEquals(request.email(), userCaptor.getValue().getEmail());
        }

        @Test
        @DisplayName("Should throw exception when user already exists")
        void shouldThrowExceptionWhenUserAlreadyExists() {
            doReturn(true).when(userRepository).existsByEmail("joe@doe.com");
            var request = new UserRequest(
                    "Joe Doe",
                    "joe@doe.com",
                    "123456"
            );

            assertThrows(UserAlreadyExistsException.class, () -> userService.create(request));
        }

    }

    @Nested
    @DisplayName("Get user by email")
    class GetByEmail {

        @Test
        @DisplayName("Should return user successfully")
        void shouldReturnUserSuccessfully() {
            var user = new User(
                    UUID.randomUUID(),
                    "Joe Doe",
                    "joe@doe.com",
                    passwordEncoder.encode("123456")
            );

            doReturn(user).when(userRepository).findByEmail(user.getEmail());

            var response = userService.getByEmail(user.getEmail());

            assertNotNull(response);
            assertEquals(user.getId(), response.id());
            assertEquals(user.getName(), response.name());
            assertEquals(user.getEmail(), response.email());
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void shouldThrowExceptionWhenUserNotFound() {
            var email = "joe@doe.com";
            doReturn(null).when(userRepository).findByEmail(email);

            assertThrows(UserNotFoundException.class, () -> userService.getByEmail(email));
        }
    }

    @Nested
    @DisplayName("Get all users")
    class GetAll {

        @Test
        @DisplayName("Should return all users successfully")
        void shouldReturnAllUsersSuccessfully() {
            var users = List.of(
                    new User(
                            UUID.randomUUID(),
                            "Joe Doe",
                            "joe@doe.com",
                            passwordEncoder.encode("123456")
                    ),
                    new User(
                            UUID.randomUUID(),
                            "John Doe",
                            "john@doe.com",
                            passwordEncoder.encode("123456")
                    )
            );

            doReturn(users).when(userRepository).findAll();

            var response = userService.getAll();

            assertNotNull(response);
            assertEquals(users.size(), response.size());
        }
    }

    @Nested
    @DisplayName("Update user")
    class Update {

        @Test
        @DisplayName("Should update user successfully")
        void shouldUpdateUserSuccessfully() {
            var user = new User(
                    UUID.randomUUID(),
                    "Joe Doe",
                    "joe@doe.com",
                    passwordEncoder.encode("123456")
            );

            doReturn(Optional.of(user)).when(userRepository).findById(user.getId());

            assertNotNull(user);

            doReturn(user).when(userRepository).save(userCaptor.capture());
            var request = new UserRequest(
                    "John Doe",
                    "joe@doe.com",
                    "123456"
            );

            var response = userService.update(user.getId(), request);

            assertNotNull(response);
            assertEquals(request.name(), userCaptor.getValue().getName());
            assertEquals(request.email(), userCaptor.getValue().getEmail());
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void shouldThrowExceptionWhenUserNotFound() {
            var randomUUID = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(randomUUID);

            var request = new UserRequest(
                    "John Doe",
                    "joe@doe.com",
                    "123456"
            );

            assertThrows(UserNotFoundException.class, () -> userService.update(randomUUID, request));
        }
    }


    @Nested
    @DisplayName("Get user by id")
    class GetById {

        @Test
        @DisplayName("Should return user successfully")
        void shouldReturnUserSuccessfully() {
            var user = new User(
                    UUID.randomUUID(),
                    "Joe Doe",
                    "joe@doe",
                    passwordEncoder.encode("123456")
            );

            doReturn(Optional.of(user)).when(userRepository).findById(user.getId());

            var response = userService.getById(user.getId());

            assertNotNull(response);
            assertEquals(user.getId(), response.id());
            assertEquals(user.getName(), response.name());
            assertEquals(user.getEmail(), response.email());
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void shouldThrowExceptionWhenUserNotFound() {
            var randomUUID = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(randomUUID);

            assertThrows(UserNotFoundException.class, () -> userService.getById(randomUUID));
        }
    }
}
