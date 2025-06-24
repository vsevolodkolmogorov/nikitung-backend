package ru.nikitung.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nikitung.comment.dto.CommentResponse;
import ru.nikitung.comment.service.CommentService;

import java.util.List;
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/getAllByPlaceId/{placeId}")
    public List<CommentResponse> findAllByPlaceId(@PathVariable Long placeId) {
        return commentService.findAllByPlaceId(placeId);
    }
}
