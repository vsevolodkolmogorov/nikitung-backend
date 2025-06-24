package ru.nikitung.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nikitung.user.dto.UserResponse;
import ru.nikitung.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<UserResponse> findUserByEmail(String email, String secret) {

        if (!"very-secret-key".equals(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return userRepository.findByEmail(email)
                .map(user -> ResponseEntity.ok(new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole()
                )))
                .orElse(ResponseEntity.notFound().build());
    }
}
