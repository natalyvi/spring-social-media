package com.example.bitter.service.impl;

import com.example.bitter.service.HashtagService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.bitter.repository.HashtagRepository;
import com.example.bitter.mapper.HashtagMapper;
@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService{
    
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

}
