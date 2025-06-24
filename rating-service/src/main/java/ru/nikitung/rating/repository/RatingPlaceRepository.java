package ru.nikitung.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nikitung.rating.model.RatingPlace;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "rating_place")
public interface RatingPlaceRepository extends JpaRepository<RatingPlace, Long> {
    RatingPlace findByPlaceId(Long placeId);
}
