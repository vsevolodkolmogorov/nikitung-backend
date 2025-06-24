package ru.nikitung.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.nikitung.gateway.dto.*;
import ru.nikitung.gateway.security.CustomReactiveUserDetailsService;

import java.util.List;

@RestController
@RequestMapping("/api/aggregated")
@RequiredArgsConstructor
public class PlaceAggregatorController {

    private final WebClient.Builder webClientBuilder;

    @GetMapping("/places/{id}")
    public Mono<PlaceWithReviewsResponse> getPlaceWithReviews(@PathVariable Long id) {
        WebClient webClient = webClientBuilder.build();

        Mono<PlaceResponse> placeMono = webClient.get()
                .uri("http://place-service/place/" + id)
                .retrieve()
                .bodyToMono(PlaceResponse.class);

        Mono<List<CommentResponse>> commentMono = webClient.get()
                .uri("http://comment-service/comment/getAllByPlaceId/" + id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CommentResponse>>() {});

        Mono<RatingPlaceResponse> ratingCommonPlaceMono = webClient.get()
                .uri("http://rating-service/rating_place/" + id)
                .retrieve()
                .bodyToMono(RatingPlaceResponse.class);

        Mono<List<RatingCategoryAverageDto>> ratingOfAllCategorysMono = webClient.get()
                .uri("http://rating-service/rating/place/" + id + "/averages")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RatingCategoryAverageDto>>() {});


        return Mono.zip(placeMono, commentMono, ratingCommonPlaceMono, ratingOfAllCategorysMono)
                .map(tuple -> new PlaceWithReviewsResponse(tuple.getT1(), tuple.getT2(), tuple.getT3(), tuple.getT4()));
    }
}
