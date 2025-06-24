package ru.nikitung.comment.dto;

import lombok.Data;

@Data
public class CommentResponse {
    private String text;
    private Long userId;
    private Long placeId;
    private String userEmail;
}
