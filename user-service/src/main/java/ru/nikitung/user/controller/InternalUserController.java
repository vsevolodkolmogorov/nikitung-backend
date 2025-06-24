package ru.nikitung.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikitung.user.dto.UserResponse;
import ru.nikitung.user.service.UserService;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam String email, @RequestHeader("X-INTERNAL-SECRET") String secret) {
        return userService.findUserByEmail(email, secret);
    }
}
