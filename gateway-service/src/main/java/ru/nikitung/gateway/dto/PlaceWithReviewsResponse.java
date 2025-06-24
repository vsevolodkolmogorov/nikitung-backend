package ru.nikitung.gateway.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PlaceWithReviewsResponse {
    private String title;
    private String region;
    private String city;
    private String description;
    private AccessZone accessZone;
    private String publicTransportDescription;
    private Set<InfrastructureFeature> infrastructure;

    private Double averageRating;
    private Integer totalRatings;
    private List<CommentResponse> comments;
    private List<RatingCategoryAverageDto> placeCategoryAVG;

    public PlaceWithReviewsResponse(PlaceResponse place,
                                    List<CommentResponse> comments,
                                    RatingPlaceResponse rating,
                                    List<RatingCategoryAverageDto> placeCategoryAVG) {
        this.title = place.getTitle();
        this.region = place.getRegion();
        this.city = place.getCity();
        this.description = place.getDescription();
        this.accessZone = place.getAccessZone();
        this.publicTransportDescription = place.getPublicTransportDescription();
        this.infrastructure = place.getInfrastructure();

        this.averageRating = rating.getAverageScore();
        this.totalRatings = rating.getTotalRatings();

        this.comments = comments;
        this.placeCategoryAVG = placeCategoryAVG;
    }
}
