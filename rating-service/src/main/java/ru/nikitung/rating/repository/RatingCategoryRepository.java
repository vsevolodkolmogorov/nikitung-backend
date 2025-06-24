package ru.nikitung.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nikitung.rating.model.RatingCategory;

import java.util.Optional;

@RepositoryRestResource(path = "rating_category")
public interface RatingCategoryRepository extends JpaRepository<RatingCategory, Long> {
    Optional<RatingCategory> findByCode(String code);
}
