package api.moneyflow.user.controller;

import api.moneyflow.user.payload.LoginRequestPayload;
import api.moneyflow.user.payload.LoginResponsePayload;
import api.moneyflow.user.repository.UserRepository;
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
public class TokenController {
    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenController(UserRepository userRepository, JwtEncoder jwtEncoder, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<LoginResponsePayload> login(@RequestBody @Valid LoginRequestPayload payload) {
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

        return ResponseEntity.ok().body(new LoginResponsePayload(jwtValue));
    }

    private boolean isLoginCorrect(String email, String password) {
        return passwordEncoder.matches(password, userRepository.findByEmail(email).getPassword());
    }

}