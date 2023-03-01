package com.example.bitter.service.impl;

import com.example.bitter.dto.CredentialsDto;
import com.example.bitter.dto.TweetRequestDto;
import com.example.bitter.dto.TweetResponseDto;
import com.example.bitter.mapper.TweetMapper;
import com.example.bitter.repository.TweetRepository;
import com.example.bitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    // Must be in reverse-chronological order
    @Override
    public List<TweetResponseDto> getAllTweets() {
        return null;
    }

    // Throw error if no such tweet exists or is deleted
    @Override
    public TweetResponseDto getTweet(Integer id) {
        return null;
    }

    // Create simple tweet, w/ author set to the user identified by the credentials in the request body
    // Must contain content property WITHOUT inReplyTo or repostOf, otherwise throw error
    // Must parse @usernames and #hashtags
    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        return null;
    }

    // Throw error is the tweet is deleted or doesn't exist, or if the credentials don't match an active user in the DB
    // On successful operation, return no response body
    @Override
    public void likeTweet(Integer id, CredentialsDto credentialsDto) {

    }
}
