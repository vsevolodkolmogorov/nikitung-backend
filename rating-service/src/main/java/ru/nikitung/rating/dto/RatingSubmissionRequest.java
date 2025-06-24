package ru.nikitung.rating.dto;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingSubmissionRequest {
    private Long userId;
    private Long placeId;
    private Map<String, Double> scores;
}
