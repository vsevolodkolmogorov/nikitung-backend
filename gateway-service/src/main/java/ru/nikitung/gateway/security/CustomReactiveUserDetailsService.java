package ru.nikitung.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.nikitung.gateway.dto.UserResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return webClientBuilder.build()
                .get()
                .uri("http://user-service/internal/users/by-email?email=" + username)
                .header("X-INTERNAL-SECRET", "very-secret-key")
                .retrieve()
                .bodyToMono(UserResponse.class)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        "",
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getCode()))
                ));
    }
}
