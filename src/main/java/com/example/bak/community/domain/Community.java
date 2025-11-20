package com.example.bak.community.domain;

import com.example.bak.company.domain.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "communities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String jobGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    private Community(Long id, String name, String jobGroup, Company company) {
        this.id = id;
        this.name = name;
        this.jobGroup = jobGroup;
        this.company = company;
    }

    private Community(String name, String jobGroup, Company company) {
        this.name = name;
        this.jobGroup = jobGroup;
        this.company = company;
    }

    public static Community create(String name, String jobGroup, Company company) {
        return new Community(name, jobGroup, company);
    }

    public static Community testInstance(Long id, String name, String jobGroup, Company company) {
        return new Community(id, name, jobGroup, company);
    }

    public void update(String name, String jobGroup) {
        this.name = name;
        this.jobGroup = jobGroup;
    }
}
