package ru.nikitung.auth.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}

