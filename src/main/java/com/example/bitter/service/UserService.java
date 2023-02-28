package com.example.bitter.service;

import com.example.bitter.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
}
