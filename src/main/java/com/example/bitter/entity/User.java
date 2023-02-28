package com.example.bitter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "user_table")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Embedded
    private Credentials credentials;

    @CreationTimestamp
    private Timestamp joined;

    private boolean deleted;

    @Embedded
    private Profile profile;

    @OneToMany(mappedBy = "author")
    private Set<Tweet> tweets;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private Set<Tweet> likes;

    @ManyToMany
    @JoinTable(
            name = "user_mentions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private Set<Tweet> mentions;

    @ManyToMany
    @JoinTable(
            name = "followers_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> followers;

    @ManyToMany(mappedBy = "followers")
    private Set<User> following;

}
