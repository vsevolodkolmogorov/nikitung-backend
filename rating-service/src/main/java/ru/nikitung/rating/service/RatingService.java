package ru.nikitung.rating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nikitung.rating.dto.RatingCategoryAverageDto;
import ru.nikitung.rating.dto.RatingSubmissionRequest;
import ru.nikitung.rating.model.RatingCategory;
import ru.nikitung.rating.model.RatingCategoryScore;
import ru.nikitung.rating.model.RatingPlace;
import ru.nikitung.rating.model.ScoreSnapshot;
import ru.nikitung.rating.repository.RatingCategoryRepository;
import ru.nikitung.rating.repository.RatingPlaceRepository;
import ru.nikitung.rating.repository.ScoreSnapshotRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingPlaceRepository ratingPlaceRepository;
    private final ScoreSnapshotRepository scoreSnapshotRepository;
    private final RatingCategoryRepository ratingCategoryRepository;

    private static final double RATING_SCALE = 10.0;
    private static final long DEFAULT_USER_ID = -1L;
    private static final double ZERO_SCORE = 0.0;


    public void processSubmission(RatingSubmissionRequest request) {
        Optional<ScoreSnapshot> existingRating = scoreSnapshotRepository.findByUserIdAndPlaceId(
                request.getUserId(), request.getPlaceId());

        if (existingRating.isPresent()) {
            updateRating(existingRating.get(), request);
        } else {
            createNewRating(request);
        }

        updatePlaceRating(request.getPlaceId());
    }

    private void createNewRating(RatingSubmissionRequest request) {
        List<RatingCategoryScore> scores = buildCategoryScores(request);

        ScoreSnapshot snapshot = ScoreSnapshot.builder()
                .userId(request.getUserId() != null ? request.getUserId() : DEFAULT_USER_ID)
                .placeId(request.getPlaceId())
                .totalScore(calculateNikitungScore(scores))
                .validForCalculation(isEmptyScore(request))
                .build();

        linkScoresToSnapshot(scores, snapshot);
        scoreSnapshotRepository.save(snapshot);
    }

    private void updateRating(ScoreSnapshot existingSnapshot, RatingSubmissionRequest request) {
        List<RatingCategoryScore> existingScores = existingSnapshot.getScores();
        existingScores.clear();

        for (Map.Entry<String, Double> entry : request.getScores().entrySet()) {
            RatingCategory category = ratingCategoryRepository.findByCode(entry.getKey()).orElseThrow();
            RatingCategoryScore score = RatingCategoryScore.builder()
                    .category(category)
                    .score(entry.getValue())
                    .snapshot(existingSnapshot)
                    .build();
            existingScores.add(score);
        }

        existingSnapshot.setTotalScore(calculateNikitungScore(existingScores));
        existingSnapshot.setValidForCalculation(isEmptyScore(request));
        scoreSnapshotRepository.save(existingSnapshot);
    }

    private List<RatingCategoryScore> buildCategoryScores(RatingSubmissionRequest request) {
        List<RatingCategoryScore> scores = new ArrayList<>();

        for (Map.Entry<String, Double> entry : request.getScores().entrySet()) {
            RatingCategory category = ratingCategoryRepository.findByCode(entry.getKey()).orElseThrow();
            scores.add(RatingCategoryScore.builder()
                    .category(category)
                    .score(entry.getValue())
                    .build());
        }

        return scores;
    }

    private void linkScoresToSnapshot(List<RatingCategoryScore> scores, ScoreSnapshot snapshot) {
        scores.forEach(score -> score.setSnapshot(snapshot));
        snapshot.setScores(scores);
    }

    private double calculateNikitungScore(List<RatingCategoryScore> scores) {
        double totalScores = scores.stream().mapToDouble(RatingCategoryScore::getScore).sum();
        double totalPossible = scores.size();
        return Math.round((totalScores / totalPossible) * RATING_SCALE) / RATING_SCALE;
    }

    private boolean isEmptyScore(RatingSubmissionRequest request) {
        return !request.getScores().values().stream().allMatch(score -> score == ZERO_SCORE);
    }

    private void updatePlaceRating(Long placeId) {
        RatingPlace ratingPlace = getOrCreateRatingPlace(placeId);
        List<ScoreSnapshot> validSnapshots = scoreSnapshotRepository
                .findByPlaceIdAndValidForCalculationTrue(placeId);

        ratingPlace.setTotalRatings(validSnapshots.size());
        ratingPlace.setAverageScore(calculateAverageScore(validSnapshots));

        ratingPlaceRepository.save(ratingPlace);
    }

    private RatingPlace getOrCreateRatingPlace(Long placeId) {
        RatingPlace ratingPlace = ratingPlaceRepository.findByPlaceId(placeId);

        if (ratingPlace == null) {
            ratingPlace = new RatingPlace();
            ratingPlace.setPlaceId(placeId);
            ratingPlace.setTotalRatings(0);
            ratingPlace.setAverageScore(ZERO_SCORE);
        }

        return ratingPlace;
    }

    private double calculateAverageScore(List<ScoreSnapshot> validSnapshots) {
        return validSnapshots.stream()
                .mapToDouble(ScoreSnapshot::getTotalScore)
                .average()
                .orElse(ZERO_SCORE);
    }

    public List<RatingCategoryAverageDto> getAverageByPlace(@PathVariable Long placeId) {
        return scoreSnapshotRepository.findAverageScoresByPlaceId(placeId)
                .stream()
                .map(p -> new RatingCategoryAverageDto(p.getCategory(), p.getAverageScore()))
                .toList();
    }

    public List<RatingCategoryAverageDto> getAverageByUser(@PathVariable Long userId) {
        return scoreSnapshotRepository.findAverageScoresByUserId(userId)
                .stream()
                .map(p -> new RatingCategoryAverageDto(p.getCategory(), p.getAverageScore()))
                .toList();
    }
}
