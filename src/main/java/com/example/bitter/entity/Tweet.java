package com.example.bitter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
    @Id
    @GeneratedValue
    private Integer id;

    private Timestamp posted; // must be generated upon creation

    private boolean deleted;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet inReplyTo;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet repostOf;

    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<Hashtag> hashtags;

    @ManyToMany(mappedBy = "likes")
    private Set<User> likedBy;

    @ManyToMany(mappedBy = "mentions")
    private Set<User> mentionedBy;
}
