package ru.nikitung.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleResponse {
    private String code;

    @JsonProperty("_links")
    private Map<String, Object> links;

    public String getSelfHref() {
        if (links != null && links.containsKey("self")) {
            Object selfLink = links.get("self");
            if (selfLink instanceof Map) {
                Map<String, Object> selfMap = (Map<String, Object>) selfLink;
                return (String) selfMap.get("href");
            }
        }
        return null;
    }
}
