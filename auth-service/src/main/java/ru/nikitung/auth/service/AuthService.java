package ru.nikitung.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nikitung.auth.dto.*;
import ru.nikitung.auth.feign.UserClient;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserClient userClient;

    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            if (userExists(request.getEmail())) {
                return ResponseEntity.badRequest()
                        .body("User already exists");
            }

            RoleResponse role = userClient.findRoleByCode("USER");

            CreateUserRequest createUserRequest = new CreateUserRequest(
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword()),
                    role.getSelfHref()
            );

            UserResponse createdUser = userClient.createUser(createUserRequest);

            String jwt = jwtService.generateTokenDirectly(createdUser);

            return ResponseEntity.ok(new AuthResponse(jwt, createdUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Registration failed: " + e.getMessage());
        }
    }

    private boolean userExists(String email) {
        try {
            userClient.findUserByEmail(email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            UserResponse user = userClient.findUserByEmail(request.getEmail());

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid credentials");
            }

            String jwt = jwtService.generateTokenDirectly(user);

            return ResponseEntity.ok(new AuthResponse(jwt, user));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }

    public ResponseEntity<?> getCurrentUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

        try {
            UserResponse user = userClient.findUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }
}
