package ru.nikitung.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private RoleResponse role;

    @JsonProperty("_links")
    private Map<String, Object> links;
}