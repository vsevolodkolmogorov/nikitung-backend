package ru.nikitung.place.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nikitung.place.dto.PlaceWithRating;
import ru.nikitung.place.dto.RatingPlaceResponse;
import ru.nikitung.place.feign.RatingClient;
import ru.nikitung.place.model.AccessZone;
import ru.nikitung.place.model.InfrastructureFeature;
import ru.nikitung.place.model.Place;
import ru.nikitung.place.model.RatingSubmissionRequest;
import ru.nikitung.place.repository.PlaceRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final RatingClient ratingClient;
    private final PlaceRepository placeRepository;

    public List<Map<String, String>> getAccessZonesFeatures() {
        return Arrays.stream(AccessZone.values())
                .map(feature -> Map.of(
                        "value", feature.name(),
                        "label", feature.getLabel()
                ))
                .toList();
    }

    public List<PlaceWithRating> getAllPlaceWithRatings() {
        List<Place> places = placeRepository.findAll();
        return places.stream().map(place -> {
            RatingPlaceResponse rating = ratingClient.findRatingPlaceById(place.getId());
            PlaceWithRating dto = new PlaceWithRating();
            dto.setTitle(place.getTitle());
            dto.setAccessZone(place.getAccessZone());
            dto.setCity(place.getCity());
            dto.setRegion(place.getRegion());
            dto.setAverageScore(rating != null ? rating.getAverageScore() : 0);
            dto.setInfrastructure(place.getInfrastructure());
            dto.setDescription(place.getDescription());
            dto.setPublicTransportDescription(place.getPublicTransportDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<Map<String, String>> getLabeledInfrastructureFeatures() {
        return Arrays.stream(InfrastructureFeature.values())
                .map(feature -> Map.of(
                        "value", feature.name(),
                        "label", feature.getLabel()
                ))
                .toList();
    }

    public Place createPlace(@RequestBody Place request) {
        Place place = placeRepository.save(request);

        Map<String, Double> scores = new HashMap<>();
        scores.put("purity", 0.0);
        scores.put("logistics", 0.0);
        scores.put("vibe", 0.0);
        scores.put("temperature", 0.0);
        scores.put("impression", 0.0);

        RatingSubmissionRequest ratingSubmissionRequest = new RatingSubmissionRequest();
        ratingSubmissionRequest.setPlaceId(place.getId());
        ratingSubmissionRequest.setUserId(null);
        ratingSubmissionRequest.setScores(scores);

        ratingClient.submitRating(ratingSubmissionRequest);

        return place;
    }
}
