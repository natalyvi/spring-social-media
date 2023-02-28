package com.example.bitter.service.impl;

import com.example.bitter.dto.UserResponseDto;
import com.example.bitter.mapper.UserMapper;
import com.example.bitter.repository.UserRepository;
import com.example.bitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToDtos(userRepository.findAll());
    }
}
