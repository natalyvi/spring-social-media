package com.example.bitter.service.impl;

import com.example.bitter.dto.UserRequestDto;
import com.example.bitter.dto.UserResponseDto;
import com.example.bitter.entity.User;
import com.example.bitter.mapper.UserMapper;
import com.example.bitter.repository.UserRepository;
import com.example.bitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getCredentials().getUsername() == null ||
                userRequestDto.getCredentials().getPassword() == null ||
                userRequestDto.getProfile().getEmail() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User userToSave = userMapper.dtoToEntity(userRequestDto);
        userRepository.saveAndFlush(userToSave);

        return new ResponseEntity<>(userMapper.entityToDto(userToSave), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponseDto> getUserByUsername(String username) {
        if(!userRepository.existsByCredentials_Username(username) ||
        userRepository.findUserByCredentials_Username(username).isDeleted()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User userByCredentialsUsername = userRepository.findUserByCredentials_Username(username);

        return new ResponseEntity<>(userMapper.entityToDto(userByCredentialsUsername),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponseDto> deleteUserByusername(String username) {
        if(!userRepository.existsByCredentials_UsernameAndDeletedFalse(username)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User userToDelete = userRepository.findUserByCredentials_Username(username);
        userToDelete.setDeleted(true);
        userRepository.saveAndFlush(userToDelete);

        return new ResponseEntity<>(userMapper.entityToDto(userToDelete), HttpStatus.OK);
    }
}
