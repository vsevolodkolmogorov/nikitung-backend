package ru.nikitung.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nikitung.place.model.Place;

@RepositoryRestResource(path = "place")
public interface PlaceRepository extends JpaRepository<Place, Long> {

}
