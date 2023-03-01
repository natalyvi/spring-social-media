package com.example.bitter.controller;

import com.example.bitter.dto.UserRequestDto;
import com.example.bitter.dto.UserResponseDto;
import com.example.bitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @GetMapping("/{username}")
    public UserResponseDto getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @DeleteMapping("/{username}")
    public UserResponseDto deleteUserByUsername(@PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }

    @PatchMapping("/{username}")
    public UserResponseDto updateUserProfileByUsername(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUserProfileByUsername(username, userRequestDto);
    }
}
