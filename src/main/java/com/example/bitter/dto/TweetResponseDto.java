package com.example.bitter.dto;

import com.example.bitter.entity.Tweet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class TweetResponseDto {
    private Integer id;

    private Timestamp posted; // must be generated upon creation

    private boolean deleted;

    private String content;

    private Tweet inReplyTo;

    private Tweet repostOf;
}
