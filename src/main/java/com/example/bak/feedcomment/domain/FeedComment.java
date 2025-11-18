package com.example.bak.feedcomment.domain;

import com.example.bak.feed.domain.Feed;
import com.example.bak.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;

    private FeedComment(Long id, String comment, User author, Feed feed) {
        this.id = id;
        this.comment = comment;
        this.author = author;
        this.feed = feed;
    }

    private FeedComment(String comment, User author) {
        this.comment = comment;
        this.author = author;
    }

    public static FeedComment create(String comment, User author) {
        return new FeedComment(comment, author);
    }

    public static FeedComment testInstance(Long id, String comment, User author, Feed feed) {
        return new FeedComment(id, comment, author, feed);
    }

    public void joinFeed(Feed feed) {
        this.feed = feed;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
