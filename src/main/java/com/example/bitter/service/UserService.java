package com.example.bitter.service;

import com.example.bitter.dto.UserRequestDto;
import com.example.bitter.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto getUserByUsername(String username);

   UserResponseDto deleteUserByusername(String username);

    UserResponseDto updateUserProfileByUsername(String username, UserRequestDto userRequestDto);

}
