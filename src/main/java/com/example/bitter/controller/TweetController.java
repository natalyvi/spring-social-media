package com.example.bitter.controller;

import com.example.bitter.dto.CredentialsDto;
import com.example.bitter.dto.TweetRequestDto;
import com.example.bitter.dto.TweetResponseDto;
import com.example.bitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
    private final TweetService tweetService;

    // GET tweets
    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }

    // GET tweets/{id}
    @GetMapping("/{id}")
    public TweetResponseDto getTweet(@PathVariable Integer id) {
        return tweetService.getTweet(id);
    }

    // POST tweets
    // Create simple tweet, w/ author set to the user identified by the credentials in the request body
    // Must contain content property WITHOUT inReplyTo or repostOf, otherwise throw error
    // Must parse @usernames and #hashtags
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    // POST tweets/{id}/like
    // Throw error is the tweet is deleted or doesn't exist, or if the credentials don't match an active user in the DB
    // On successful operation, return no response body
    @PostMapping("/{id}")
    public void likeTweet(@PathVariable Integer id, CredentialsDto credentialsDto) {
        tweetService.likeTweet(id, credentialsDto);
    }
}
