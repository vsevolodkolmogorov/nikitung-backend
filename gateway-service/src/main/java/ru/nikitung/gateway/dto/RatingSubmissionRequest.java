package ru.nikitung.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
