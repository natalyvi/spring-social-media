package com.example.bitter.service;

import com.example.bitter.dto.CredentialsDto;
import com.example.bitter.dto.TweetRequestDto;
import com.example.bitter.dto.TweetResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweet(Long id);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    void likeTweet(Long id, CredentialsDto credentialsDto);
}
