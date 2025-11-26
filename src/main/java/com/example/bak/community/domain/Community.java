package com.example.bak.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column
    private Long companyId;

    private Community(Long id, String name, String jobGroup, Long companyId) {
        this.id = id;
        this.name = name;
        this.jobGroup = jobGroup;
        this.companyId = companyId;
    }

    private Community(String name, String jobGroup, Long companyId) {
        this.name = name;
        this.jobGroup = jobGroup;
        this.companyId = companyId;
    }

    public static Community create(String name, String jobGroup, Long companyId) {
        return new Community(name, jobGroup, companyId);
    }

    public static Community testInstance(Long id, String name, String jobGroup, Long companyId) {
        return new Community(id, name, jobGroup, companyId);
    }

    public void update(String name, String jobGroup) {
        this.name = name;
        this.jobGroup = jobGroup;
    }
}
