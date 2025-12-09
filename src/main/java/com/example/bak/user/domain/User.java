package com.example.bak.user.domain;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", nullable = false)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.NORMAL;

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

    public static User createInstance(Long id, String email, String password) {
        return new User(id, email, password);
    }

    public static User create(String email, String password) {
        return new User(email, password);
    }

    public static User testInstance(Long id, String email, String password) {
        return new User(id, email, password);
    }

    public void addProfile(Profile profile) {
        this.profile = profile;
    }
}
