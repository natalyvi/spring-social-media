package com.example.bitter.service;

import com.example.bitter.dto.CredentialsDto;
import com.example.bitter.dto.TweetRequestDto;
import com.example.bitter.dto.TweetResponseDto;
import com.example.bitter.dto.UserResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweet(Long id);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    void likeTweet(Long id, CredentialsDto credentialsDto);

    List<UserResponseDto> getUsersWhoLikedTweet(Long id);

    List<UserResponseDto> getTweetMentions(Long id);

    List<TweetResponseDto> getRepliesToTweet(Long id);

    TweetResponseDto repostTweet(Long id);

    List<TweetResponseDto> getRepostsOfTweet(Long id);

    TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);
}
