package ru.nikitung.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nikitung.comment.dto.CommentResponse;
import ru.nikitung.comment.feign.UserClient;
import ru.nikitung.comment.model.Comment;
import ru.nikitung.comment.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserClient userServiceClient;

    public List<CommentResponse> findAllByPlaceId(Long placeId) {
        List<Comment> comments = commentRepository.findAllByPlaceId(placeId);

        return comments.stream().map(comment -> {
            String email = userServiceClient.findUserById(comment.getUserId()).getEmail();
            CommentResponse dto = new CommentResponse();
            dto.setText(comment.getText());
            dto.setUserId(comment.getUserId());
            dto.setPlaceId(comment.getPlaceId());
            dto.setUserEmail(email);
            return dto;
        }).collect(Collectors.toList());
    }
}
