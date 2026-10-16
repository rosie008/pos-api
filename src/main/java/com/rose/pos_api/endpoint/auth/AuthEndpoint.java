package com.rose.pos_api.endpoint.auth;

import com.rose.pos_api.dto.request.auth.RequestLoginDTO;
import com.rose.pos_api.dto.response.auth.ResponseLoginDTO;
import com.rose.pos_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthEndpoint {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> login(@Valid @RequestBody RequestLoginDTO request) {
        ResponseLoginDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
