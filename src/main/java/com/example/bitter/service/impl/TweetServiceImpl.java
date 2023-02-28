package com.example.bitter.service.impl;

import com.example.bitter.mapper.TweetMapper;
import com.example.bitter.repository.TweetRepository;
import com.example.bitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
}
