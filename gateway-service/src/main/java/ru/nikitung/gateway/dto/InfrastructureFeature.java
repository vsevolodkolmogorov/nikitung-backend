package ru.nikitung.gateway.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum InfrastructureFeature {
    PARKING("Парковка"),
    TOILET("Туалет"),
    SHOWER("Душ"),
    DRESSING_ROOM("Раздевалки"),
    TRASH_BINS("Мусорки"),
    BENCHES("Лежаки"),
    CAFE("Кафе"),
    WIFI("Wi-Fi");

    private final String label;

    InfrastructureFeature(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
