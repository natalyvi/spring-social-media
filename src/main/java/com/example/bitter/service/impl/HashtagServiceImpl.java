package com.example.bitter.service.impl;

import com.example.bitter.service.HashtagService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bitter.repository.HashtagRepository;
import com.example.bitter.dto.HashtagDto;
import com.example.bitter.dto.TweetResponseDto;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTweetsWithTag'");
    }

}
