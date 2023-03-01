package com.example.bitter.repository;

import com.example.bitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    List<Tweet> findByDeletedFalseOrderByPosted();
}
