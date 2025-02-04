package api.moneyflow.user.controller;

import api.moneyflow.user.payload.LoginRequest;
import api.moneyflow.user.payload.LoginResponse;
import api.moneyflow.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/login")
//@SecurityRequirement(name = SecurityConfig.AUTHORIZATION_HEADER)
@Tag(name = "login", description = "Endpoint for login")
public class TokenController {
    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenController(UserRepository userRepository, JwtEncoder jwtEncoder, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Authenticate user and return JWT token", description = "Validates user credentials and returns a JWT token if successful.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request format or missing fields"),
            @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest payload) {
        var user = userRepository.findByEmail(payload.email());

        if (user == null || !isLoginCorrect(payload.email(), payload.password())) {
            throw new BadCredentialsException("Email or password is incorrect");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("oauth2")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok().body(new LoginResponse(jwtValue));
    }

    private boolean isLoginCorrect(String email, String password) {
        return passwordEncoder.matches(password, userRepository.findByEmail(email).getPassword());
    }

}