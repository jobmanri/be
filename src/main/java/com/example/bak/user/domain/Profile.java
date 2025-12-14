package com.example.bak.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    private Long userId;

    private Profile(Long id, String name, String nickname) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
    }

    private Profile(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }

    /**
     * this method should be used for query object - written by @Sunja-An
     */
    public static Profile createInstance(Long id, String name, String nickname) {
        return new Profile(id, name, nickname);
    }

    public static Profile create(String name, String nickname) {
        return new Profile(name, nickname);
    }

    public void assignUser(Long userId) {
        this.userId = userId;
    }
}
