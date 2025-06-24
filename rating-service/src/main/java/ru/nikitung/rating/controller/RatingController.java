package ru.nikitung.rating.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikitung.rating.dto.RatingCategoryAverageDto;
import ru.nikitung.rating.dto.RatingPlaceResponse;
import ru.nikitung.rating.dto.RatingSubmissionRequest;
import ru.nikitung.rating.model.RatingPlace;
import ru.nikitung.rating.service.RatingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping(value = "/submit")
    public ResponseEntity<?> submitRating(@RequestBody RatingSubmissionRequest request) {
        ratingService.processSubmission(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/place/{placeId}/averages")
    public List<RatingCategoryAverageDto> getAverageByPlace(@PathVariable Long placeId) {
        return ratingService.getAverageByPlace(placeId);
    }

    @GetMapping("/user/{userId}/averages")
    public List<RatingCategoryAverageDto> getAverageByUser(@PathVariable Long userId) {
        return ratingService.getAverageByUser(userId);
    }
}
