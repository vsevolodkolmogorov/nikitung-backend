package ru.nikitung.place.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nikitung.place.dto.RatingPlaceResponse;
import ru.nikitung.place.model.RatingSubmissionRequest;


@FeignClient(name = "rating-service", configuration = FeignConfig.class)
public interface RatingClient {

    @GetMapping("/rating_place/{id}")
    RatingPlaceResponse findRatingPlaceById(@PathVariable Long id);

    @PostMapping("/rating/submit")
    Void submitRating(@RequestBody RatingSubmissionRequest request);
}
