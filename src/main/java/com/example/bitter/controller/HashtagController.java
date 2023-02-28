package com.example.bitter.controller;

import com.example.bitter.service.HashtagService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class HashtagController {
    private final HashtagService hashtagService;
}