package ru.nikitung.place.dto;

import lombok.Data;

@Data
public class RatingPlaceResponse {
    private Long placeId;
    private Double averageScore;
    private Integer totalRatings;
}
