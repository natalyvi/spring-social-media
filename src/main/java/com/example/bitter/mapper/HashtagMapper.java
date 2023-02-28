package com.example.bitter.mapper;

import org.mapstruct.Mapper;

import com.example.bitter.dto.HashtagResponseDto;
import com.example.bitter.entity.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    HashtagResponseDto entityToResponseDto (Hashtag hashtag);

    Hashtag responseDtoToEntity (HashtagResponseDto hashTagResponseDto);

    // TODO: Add more if necessary
}
