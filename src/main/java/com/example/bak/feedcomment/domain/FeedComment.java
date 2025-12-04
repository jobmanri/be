package com.example.bak.feedcomment.domain;

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

@Entity(name = "feed_comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedComment {

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
    private String comment;

    private FeedComment(Long id, Long feedId, String comment, Long authorId, String authorNickname) {
        this.id = id;
        this.feedId = feedId;
        this.comment = comment;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
    }

    private FeedComment(Long feedId, String comment, Long authorId, String authorNickname) {
        this.feedId = feedId;
        this.comment = comment;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
    }

    public static FeedComment create(Long feedId, String comment, Long authorId, String authorNickname) {
        return new FeedComment(feedId, comment, authorId, authorNickname);
    }

    public static FeedComment testInstance(Long id, Long feedId, String comment, Long authorId,
            String authorNickname) {
        return new FeedComment(id, feedId, comment, authorId, authorNickname);
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    public boolean isWrittenBy(Long authorId) {
        return Objects.equals(this.authorId, authorId);
    }
}
