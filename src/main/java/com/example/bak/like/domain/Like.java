package com.example.bak.like.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes",
        uniqueConstraints = @UniqueConstraint(name = "uk_like_target_user",
                columnNames = {"target_type", "target_id", "user_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 50)
    private LikeTargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private Like(LikeTargetType targetType, Long targetId, Long userId, LocalDateTime createdAt) {
        this.targetType = targetType;
        this.targetId = targetId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public static Like create(LikeTargetType targetType, Long targetId, Long userId) {
        return new Like(targetType, targetId, userId, LocalDateTime.now());
    }
}
