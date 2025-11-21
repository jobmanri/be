package com.example.bak.user.domain;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public void matchPassword(String newPassword) {
        if (!newPassword.equals(this.password)) {
            throw new BusinessException(ErrorCode.INCORRECT_PASSWORD);
        }
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
