package com.example.bak.feed.domain;

import com.example.bak.company.domain.CompanyCommunity;
import com.example.bak.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "feeds")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedComment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyCommunity community;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    private Feed(String title, String content, CompanyCommunity community, User author) {
        this.title = title;
        this.content = content;
        this.community = community;
        this.author = author;
    }

    public static Feed create(
            String title,
            String content,
            CompanyCommunity community,
            User author
    ) {
        return new Feed(title, content, community, author);
    }

    public void addComment(FeedComment comment) {
        comment.joinFeed(this);
        this.comments.add(comment);
    }
}
