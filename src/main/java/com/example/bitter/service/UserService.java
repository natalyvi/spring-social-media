package com.example.bitter.service;

import com.example.bitter.dto.UserRequestDto;
import com.example.bitter.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequestDto);

    ResponseEntity<UserResponseDto> getUserByUsername(String username);

    ResponseEntity<UserResponseDto> deleteUserByusername(String username);

//    ResponseEntity<UserResponseDto> updateUserByUsername(String username);

}
