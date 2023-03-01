package com.example.bitter.service;

import java.util.List;

import com.example.bitter.dto.HashtagDto;
import com.example.bitter.dto.TweetResponseDto;

public interface HashtagService {

    List<HashtagDto> getAllTags();

    List<TweetResponseDto> getAllTweetsWithTag(String label);
    
}
