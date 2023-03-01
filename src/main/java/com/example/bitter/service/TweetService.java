package com.example.bitter.service;

import com.example.bitter.dto.CredentialsDto;
import com.example.bitter.dto.TweetRequestDto;
import com.example.bitter.dto.TweetResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweet(Integer id);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    void likeTweet(Integer id, CredentialsDto credentialsDto);
}
