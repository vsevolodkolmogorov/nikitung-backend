package ru.nikitung.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nikitung.auth.dto.CreateUserRequest;
import ru.nikitung.auth.dto.RoleResponse;
import ru.nikitung.auth.dto.UserResponse;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/role/search/findByCode")
    RoleResponse findRoleByCode(@RequestParam("code") String code);

    @GetMapping("/internal/users/by-email")
    UserResponse findUserByEmail(@RequestParam("email") String email);

    @PostMapping("/users")
    UserResponse createUser(@RequestBody CreateUserRequest request);
}
