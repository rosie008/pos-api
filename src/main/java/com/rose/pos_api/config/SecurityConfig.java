package com.rose.pos_api.config;

import com.rose.pos_api.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthService authService) throws Exception {

        OncePerRequestFilter jwtFilter = new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain filterChain
            ) throws ServletException, IOException {
                String header = request.getHeader(HttpHeaders.AUTHORIZATION);

                if (header == null || !header.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response);
                    return;
                }

                String token = header = header.substring(7).trim();
                if (token.isEmpty()) {
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Bearer token is empty");
                    return;
                }

                try {
                    Claims claims = authService.validateAndGetClaims(token);
                    String userId = claims.getSubject();
                    String role = claims.get("role", String.class);

                    if (userId == null || role == null) {
                        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Claims token incomplete");
                        return;
                    }

                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

                    var authentication = new UsernamePasswordAuthenticationToken(
                            userId, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }catch (JwtException | IllegalArgumentException ex){
                    SecurityContextHolder.clearContext();
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token are invalid or expired");
                    return;
                }

                filterChain.doFilter(request, response);
            }
        };

        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/**", "/error").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}
;