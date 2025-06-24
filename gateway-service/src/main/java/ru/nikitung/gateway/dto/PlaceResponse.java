package ru.nikitung.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.java.Log;

import java.util.Map;
import java.util.Set;

@Data
public class PlaceResponse {
    private String title;
    private String region;
    private String city;
    private String description;
    private AccessZone accessZone;
    private String publicTransportDescription;
    private Set<InfrastructureFeature> infrastructure;

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
