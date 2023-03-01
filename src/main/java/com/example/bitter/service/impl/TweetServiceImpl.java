package com.example.bitter.service.impl;

import com.example.bitter.dto.CredentialsDto;
import com.example.bitter.dto.TweetRequestDto;
import com.example.bitter.dto.TweetResponseDto;
import com.example.bitter.dto.UserResponseDto;
import com.example.bitter.entity.Tweet;
import com.example.bitter.entity.User;
import com.example.bitter.exception.BadRequestException;
import com.example.bitter.exception.NotFoundException;
import com.example.bitter.mapper.TweetMapper;
import com.example.bitter.mapper.UserMapper;
import com.example.bitter.repository.TweetRepository;
import com.example.bitter.repository.UserRepository;
import com.example.bitter.service.TweetService;
import com.example.bitter.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public Tweet getTweetIfExists(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty() || tweet.get().isDeleted()) throw new NotFoundException("Tweet " + id + " not found");
        return tweet.get();
    }

    // Must be in reverse-chronological order
    @Override
    public List<TweetResponseDto> getAllTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findByDeletedFalseOrderByPosted());
    }

    // Throw error if no such tweet exists or is deleted
    @Override
    public TweetResponseDto getTweet(Long id) {
        return tweetMapper.entityToDto(getTweetIfExists(id));
    }

    public Tweet parseAndUpdateMentions(Tweet tweet) {
        Pattern pattern = Pattern.compile("^(@[a-zA-Z0-9_]{1,}[\\s\\S])*$");
        Set<User> mentions = new HashSet<>();
        Matcher matcher = pattern.matcher(tweet.getContent());

        // parse usernames
        while (matcher.find()) {
            String username = matcher.group().substring(1);
            mentions.add(userMapper.responseToEntity(userService.getUserByUsername(username).getBody()));
        }
        // update tweet mentions for each user
        for (User user : mentions) {
            List<Tweet> t = user.getMentions();
            t.add(tweet);
            user.setMentions(t);
            userRepository.saveAndFlush(user);
        }
        // update tweet
        Set<User> u = tweet.getMentioned();
        u.addAll(mentions);
        tweet.setMentioned(u);

        return tweetRepository.saveAndFlush(tweet);
    }

    // Create simple tweet, w/ author set to the user identified by the credentials in the request body
    // Must contain content property and proper credentials, otherwise throw error
    // Must parse @usernames and #hashtags
    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        // check if user exists
        User user;
        user = userMapper.responseToEntity(userService.getUserByUsername(tweetRequestDto.getCredentials().getUsername()).getBody());
        if (user == null) throw new BadRequestException("Invalid credentials");

        Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);
        if (tweet.getContent() == null) throw new BadRequestException("New tweet must contain content");

        tweet.setAuthor(user);

        // TODO: parse hashtags and mentions
        Tweet updatedTweet = parseAndUpdateMentions(tweet);

        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }

    // Throw error is the tweet is deleted or doesn't exist, or if the credentials don't match an active user in the DB
    // On successful operation, return no response body
    @Override
    public void likeTweet(Long id, CredentialsDto credentialsDto) {
        Tweet tweet = getTweetIfExists(id);
        User user;
        user = userMapper.responseToEntity(userService.getUserByUsername(credentialsDto.getUsername()).getBody());
        if (user == null) throw new BadRequestException("Invalid credentials");

        Set<User> u = tweet.getLikedBy();
        u.add(user);
        tweet.setLikedBy(u);
        Tweet savedTweet = tweetRepository.saveAndFlush(tweet);

        List<Tweet> t = user.getLikes();
        t.add(savedTweet);
        userRepository.saveAndFlush(user);
    }


    @Override
    public List<UserResponseDto> getUsersWhoLikedTweet(Long id) {
        Tweet tweet = getTweetIfExists(id);
        List<User> users = new ArrayList<>(tweet.getLikedBy());
        users.removeIf(User::isDeleted);
        return userMapper.entitiesToDtos(users);
    }

    @Override
    public List<UserResponseDto> getTweetMentions(Long id) {
        Tweet tweet = getTweetIfExists(id);
        List<User> users = new ArrayList<>(tweet.getMentioned());
        users.removeIf(User::isDeleted);
        return userMapper.entitiesToDtos(users);
    }

    @Override
    public List<TweetResponseDto> getRepliesToTweet(Long id) {
        Tweet tweet = getTweetIfExists(id);
        return tweetMapper.entitiesToDtos(tweet.getReplies());
    }

    @Override
    public TweetResponseDto repostTweet(Long id) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getRepostsOfTweet(Long id) {
        Tweet tweet = getTweetIfExists(id);
        return tweetMapper.entitiesToDtos(tweet.getReposts());
    }

    @Override
    public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
        Tweet tweet = getTweetIfExists(id);
        tweet.setDeleted(true);
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }
}
