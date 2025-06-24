package ru.nikitung.place.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nikitung.place.dto.PlaceWithRating;
import ru.nikitung.place.model.Place;
import ru.nikitung.place.service.PlaceService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/access-zones-labeled")
    public List<Map<String, String>> getAccessZonesFeatures() {
        return placeService.getAccessZonesFeatures();
    }

    @GetMapping("/with-average-score")
    public List<PlaceWithRating> getAllPlaceWithRatings() {
        return placeService.getAllPlaceWithRatings();
    }

    @GetMapping("/infrastructure-features-labeled")
    public List<Map<String, String>> getLabeledInfrastructureFeatures() {
        return placeService.getLabeledInfrastructureFeatures();
    }

    @PostMapping("/create-with-rating")
    public Place createPlace(@RequestBody Place request) {
        return placeService.createPlace(request);
    }
}
