package ru.nikitung.place.model;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String city;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessZone accessZone;

    @Column(length = 500)
    private String publicTransportDescription;

    @ElementCollection(targetClass = InfrastructureFeature.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "place_infrastructure", joinColumns = @JoinColumn(name = "place_id"))
    @Column(name = "feature")
    private Set<InfrastructureFeature> infrastructure;
}
