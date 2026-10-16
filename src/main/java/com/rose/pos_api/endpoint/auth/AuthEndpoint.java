package com.rose.pos_api.endpoint.auth;

import com.rose.pos_api.dto.request.auth.RequestLoginDTO;
import com.rose.pos_api.dto.response.auth.ResponseLoginDTO;
import com.rose.pos_api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthEndpoint {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    // Demo hash BCrypt untuk password "password123".
    // Untuk aplikasi nyata, simpan hash hasil pembuatan password di database.
    private final String demoPasswordHash =
            "$2a$10$vYR4pPQqR/oZcUDZfXrahecEejQHY0kLkDB5s.FctPRMcEMh1PYhG";

    public AuthEndpoint(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseLoginDTO login(@RequestBody RequestLoginDTO request) {
        boolean validUser = "budi".equals(request.getUsername())
                && passwordEncoder.matches(request.getPassword(), demoPasswordHash);

        if (!validUser) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Username atau password salah"
            );
        }

        String token = authService.generateToken("101", "USER");
        return ResponseLoginDTO.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(900)
                .build();
    }
}
