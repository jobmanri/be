package com.example.bak.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "communities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyCommunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String jobGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
    
    private CompanyCommunity(String name, String jobGroup) {
        this.name = name;
        this.jobGroup = jobGroup;
    }

    public static CompanyCommunity create(String name, String jobGroup) {
        return new CompanyCommunity(name, jobGroup);
    }
}
