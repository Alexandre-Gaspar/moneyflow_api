package api.moneyflow.user.controller;

import api.moneyflow.user.payload.UserRequestPayload;
import api.moneyflow.user.payload.UserResponsePayload;
import api.moneyflow.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponsePayload>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<UserResponsePayload> registerUser(@RequestBody @Valid UserRequestPayload payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(payload));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponsePayload> updateUser(@PathVariable UUID userId, @RequestBody @Valid UserRequestPayload payload) {
        return ResponseEntity.ok(userService.update(userId, payload));
    }
}
