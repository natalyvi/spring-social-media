package com.example.bitter.mapper;

import com.example.bitter.dto.HashtagDto;
import org.mapstruct.Mapper;

import com.example.bitter.entity.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    HashtagDto entityToDto (Hashtag hashtag);

    Hashtag dtoToEntity (HashtagDto hashtagDto);

    // TODO: Add more if necessary
}
