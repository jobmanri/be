package com.example.bak.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feed_id", nullable = false)
    private Long feedId;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String authorNickname;

    @Column(nullable = false)
    private String content;

    private Comment(Long id, Long feedId, String content, Long authorId,
            String authorNickname) {
        this.id = id;
        this.feedId = feedId;
        this.content = content;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
    }

    private Comment(Long feedId, String content, Long authorId, String authorNickname) {
        this.feedId = feedId;
        this.content = content;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
    }

    public static Comment create(Long feedId, String comment, Long authorId,
            String authorNickname) {
        return new Comment(feedId, comment, authorId, authorNickname);
    }

    public static Comment testInstance(Long id, Long feedId, String comment, Long authorId,
            String authorNickname) {
        return new Comment(id, feedId, comment, authorId, authorNickname);
    }

    public void updateComment(String comment) {
        this.content = comment;
    }

    public boolean isWrittenBy(Long authorId) {
        return Objects.equals(this.authorId, authorId);
    }
}
