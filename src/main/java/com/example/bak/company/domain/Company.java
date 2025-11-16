package com.example.bak.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "companies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CompanyCommunity> communities = new ArrayList<>();

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

    public void addCommunity(CompanyCommunity community) {
        this.communities.add(community);
    }
}
