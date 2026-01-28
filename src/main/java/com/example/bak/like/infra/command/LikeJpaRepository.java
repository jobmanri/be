package com.example.bak.like.infra.command;

import com.example.bak.like.domain.Like;
import com.example.bak.like.domain.LikeTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeJpaRepository extends JpaRepository<Like, Long> {

    boolean existsByTargetTypeAndTargetIdAndUserId(LikeTargetType targetType, Long targetId, Long userId);

    void deleteByTargetTypeAndTargetIdAndUserId(LikeTargetType targetType, Long targetId, Long userId);
}
