package ru.nikitung.place.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum AccessZone {
    CITY("Город"),
    SUBURBAN("Пригород"),
    RURAL("За городом");

    private final String label;

    AccessZone(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
