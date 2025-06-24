package ru.nikitung.place.dto;

import lombok.Data;
import ru.nikitung.place.model.AccessZone;
import ru.nikitung.place.model.InfrastructureFeature;

import java.util.Set;

@Data
public class PlaceWithRating {
    private String title;
    private String region;
    private String city;
    private String description;
    private AccessZone accessZone;
    private String publicTransportDescription;
    private Set<InfrastructureFeature> infrastructure;
    private Double averageScore;
}
