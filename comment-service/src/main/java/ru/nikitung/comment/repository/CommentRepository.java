package ru.nikitung.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nikitung.comment.model.Comment;

import java.util.List;

@RepositoryRestResource(path = "comment")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPlaceId(Long placeId);
}
