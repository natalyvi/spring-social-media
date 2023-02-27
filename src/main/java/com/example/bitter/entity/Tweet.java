package com.example.bitter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

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

    // TODO: add User and Hashtag imports

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
}
