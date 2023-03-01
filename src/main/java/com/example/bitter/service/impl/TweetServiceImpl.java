package com.example.bitter.service.impl;

import com.example.bitter.dto.CredentialsDto;
import com.example.bitter.dto.TweetRequestDto;
import com.example.bitter.dto.TweetResponseDto;
import com.example.bitter.entity.Tweet;
import com.example.bitter.entity.User;
import com.example.bitter.exception.BadRequestException;
import com.example.bitter.exception.NotFoundException;
import com.example.bitter.mapper.TweetMapper;
import com.example.bitter.repository.TweetRepository;
import com.example.bitter.service.TweetService;
import com.example.bitter.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    private final UserServiceImpl userService;


    // Must be in reverse-chronological order
    @Override
    public List<TweetResponseDto> getAllTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findByDeletedFalseOrderByPosted());
    }

    // Throw error if no such tweet exists or is deleted
    @Override
    public TweetResponseDto getTweet(Integer id) {
        return null;
    }

    // Create simple tweet, w/ author set to the user identified by the credentials in the request body
    // Must contain content property and proper credentials, otherwise throw error
    // Must parse @usernames and #hashtags
    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        // check if user exists
        User user = null;
        try {
            user = userService.getUser(tweetRequestDto.getCredentials().getUsername());
        } catch (NotFoundException e) {
            throw e;
        }

        Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);
        if (tweet.getContent() == null) throw new BadRequestException("New tweet must contain content");

        tweet.setAuthor(user);
        tweet.setPosted(new Timestamp(Instant.now().getEpochSecond()));

        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }

    // Throw error is the tweet is deleted or doesn't exist, or if the credentials don't match an active user in the DB
    // On successful operation, return no response body
    @Override
    public void likeTweet(Integer id, CredentialsDto credentialsDto) {

    }
}
