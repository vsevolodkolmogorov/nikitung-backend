package ru.nikitung.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nikitung.rating.model.RatingCategoryScore;

@RepositoryRestResource(path = "rating_category_score")
public interface RatingScoreRepository extends JpaRepository<RatingCategoryScore, Long> { }
