package com.example.bak.feed.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "feeds")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long communityId;

    @Column(nullable = false)
    private Long authorId;

    private Feed(Long id, String title, String content, Long communityId, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.communityId = communityId;
        this.authorId = authorId;
    }

    private Feed(String title, String content, Long communityId, Long authorId) {
        this.title = title;
        this.content = content;
        this.communityId = communityId;
        this.authorId = authorId;
    }

    public static Feed create(
            String title,
            String content,
            Long communityId,
            Long authorId
    ) {
        return new Feed(title, content, communityId, authorId);
    }

    public static Feed testInstance(
            Long id,
            String title,
            String content,
            Long communityId,
            Long userId
    ) {
        return new Feed(id, title, content, communityId, userId);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
