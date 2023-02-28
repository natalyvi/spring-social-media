package com.example.bitter.dto;

import com.example.bitter.entity.Tweet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ContextDto {
    private Tweet target;

    // must be in chronological order
    private List<Tweet> before;
    private List<Tweet> after;
}
