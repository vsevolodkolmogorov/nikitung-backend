package ru.nikitung.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nikitung.rating.model.ScoreSnapshot;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "rating_snapshot")
public interface ScoreSnapshotRepository extends JpaRepository<ScoreSnapshot, Long> {
    @Query("""
    SELECT r.category.code AS category,
           AVG(r.score) AS averageScore
    FROM RatingCategoryScore r
    WHERE r.snapshot.placeId = :placeId
        AND r.snapshot.validForCalculation = true
    GROUP BY r.category.code
    """)
    List<RatingCategoryAverage> findAverageScoresByPlaceId(@Param("placeId") Long placeId);

    @Query("""
    SELECT r.category.code AS category,
           AVG(r.score) AS averageScore
    FROM RatingCategoryScore r
    WHERE r.snapshot.userId = :userId
    GROUP BY r.category.code
    """)
    List<RatingCategoryAverage> findAverageScoresByUserId(@Param("userId") Long userId);

    Optional<ScoreSnapshot> findByUserIdAndPlaceId(@Param("userId") Long userId, @Param("placeId") Long placeId);

    List<ScoreSnapshot> findByPlaceIdAndValidForCalculationTrue(Long placeId);
}
