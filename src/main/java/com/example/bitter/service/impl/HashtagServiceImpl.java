package com.example.bitter.service.impl;

import com.example.bitter.service.HashtagService;

import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bitter.repository.HashtagRepository;
import com.example.bitter.dto.HashtagDto;
import com.example.bitter.dto.TweetResponseDto;
import com.example.bitter.entity.Hashtag;
import com.example.bitter.entity.Tweet;
import com.example.bitter.mapper.HashtagMapper;
@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService{
    
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    
    @Override
    public List<HashtagDto> getAllTags() {
        return hashtagMapper.entitiesToDto(hashtagRepository.findAll());
    }
    @Override
    public List<TweetResponseDto> getAllTweetsWithTag(String label) {
        // TODO: Will need methods from TweetService
        return null;
    }
    @Override
    public HashtagDto updateTag(Tweet tweet, String label) {
        Optional<Hashtag> tag = hashtagRepository.findByLabel(label);
        Hashtag hashtagEntity;
        Date date = new Date();
        if (tag.isEmpty()) {

            //Create new Hashtag entry
            hashtagEntity = new Hashtag();
            hashtagEntity.setLabel(label);
            hashtagEntity.setFirstUsed(new Timestamp(date.getTime()));

        } else {

            // Update existing hashtag
            hashtagEntity = tag.get();
            
        }
        hashtagEntity.setLastUsed(new Timestamp(date.getTime()));
        hashtagEntity.getTweets().add(tweet);
        return hashtagMapper.entityToDto(hashtagRepository.saveAndFlush(hashtagEntity));
    }
}

