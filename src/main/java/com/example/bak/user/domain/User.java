package com.example.bak.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static User create(String email, String password, String name, String nickname) {
        User user = new User(email, password);
        Profile profile = Profile.create(name, nickname, user);
        user.addProfile(profile);
        return user;
    }

    public static User testInstance(Long id, String email, String password, String name,
            String nickname) {
        User user = new User(id, email, password);
        Profile profile = Profile.create(name, nickname, user);
        user.addProfile(profile);
        return user;
    }

    private void addProfile(Profile profile) {
        this.profile = profile;
    }
}
