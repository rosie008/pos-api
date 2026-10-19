package com.rose.pos_api.service;

import com.rose.pos_api.dto.request.auth.RequestLoginDTO;
import com.rose.pos_api.dto.response.auth.ResponseLoginDTO;
import com.rose.pos_api.model.user.User;
import com.rose.pos_api.repository.IUserRepository;
import com.rose.pos_api.statval.constant.PosConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {

    private final SecretKey signingKey;
    private final long expirationMillis = PosConstants.JWT_EXPIRATION;
    private final String issuer = PosConstants.JWT_ISSUER;
    private final String audience = PosConstants.JWT_AUDIENCE;

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            @Value("${jwt.key}") String secretBase64,
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretBase64);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseLoginDTO login(RequestLoginDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Username atau password salah"
                ));

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Akun pengguna tidak aktif"
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Username atau password salah"
            );
        }

        String token = generateToken(String.valueOf(user.getId()), user.getRole().name());

        return ResponseLoginDTO.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(expirationMillis / 1000)
                .build();
    }

    public String generateToken(String userId, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .issuer(issuer)
                .audience().add(audience).and()
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey)
                .compact();
    }

    public Claims validateAndGetClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .requireIssuer(issuer)
                .requireAudience(audience)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
