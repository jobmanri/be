package com.example.bak.feedcomment.domain;

import com.example.bak.feed.domain.Feed;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String authorNickname;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;

    private FeedComment(Long id, String comment, Long authorId, String authorNickname, Feed feed) {
        this.id = id;
        this.comment = comment;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
        this.feed = feed;
    }

    private FeedComment(String comment, Long authorId, String authorNickname) {
        this.comment = comment;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
    }

    public static FeedComment create(String comment, Long authorId, String authorNickname) {
        return new FeedComment(comment, authorId, authorNickname);
    }

    public static FeedComment testInstance(Long id, String comment, Long authorId,
            String authorNickname, Feed feed) {
        return new FeedComment(id, comment, authorId, authorNickname, feed);
    }

    public void joinFeed(Feed feed) {
        this.feed = feed;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    public boolean isWrittenBy(Long authorId) {
        return Objects.equals(this.authorId, authorId);
    }
}
