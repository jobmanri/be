package com.example.bak.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "companies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String careerLink;

    @Column(nullable = false)
    private String logoUrl;

    @Column(nullable = false)
    private String description;

    private Company(Long id, String name, String careerLink, String logoUrl, String description) {
        this.id = id;
        this.name = name;
        this.careerLink = careerLink;
        this.logoUrl = logoUrl;
        this.description = description;
    }

    private Company(String name, String careerLink, String logoUrl, String description) {
        this.name = name;
        this.careerLink = careerLink;
        this.logoUrl = logoUrl;
        this.description = description;
    }

    public static Company create(
            String name,
            String careerLink,
            String logoUrl,
            String description
    ) {
        return new Company(name, careerLink, logoUrl, description);
    }

    public static Company testInstance(
            Long id,
            String name,
            String careerLink,
            String logoUrl,
            String description
    ) {
        return new Company(id, name, careerLink, logoUrl, description);
    }

    public void update(String name, String careerLink, String logoUrl, String description) {
        this.name = name;
        this.careerLink = careerLink;
        this.logoUrl = logoUrl;
        this.description = description;
    }
}
