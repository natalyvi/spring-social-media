package com.example.bitter.service.impl;

import com.example.bitter.dto.UserRequestDto;
import com.example.bitter.dto.UserResponseDto;
import com.example.bitter.entity.User;
import com.example.bitter.exception.BadRequestException;
import com.example.bitter.exception.NotAuthorizedException;
import com.example.bitter.exception.NotFoundException;
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
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getCredentials().getUsername() == null) {
            throw new BadRequestException("The provided username is null.");
        }
        if (userRequestDto.getCredentials().getPassword() == null) {
            throw new BadRequestException("The provided password is null.");
        }
        if (userRequestDto.getProfile().getEmail() == null) {
            throw new BadRequestException("The provided email is null.");
        }
        User userToSave = userMapper.dtoToEntity(userRequestDto);
        userRepository.saveAndFlush(userToSave);

        return userMapper.entityToDto(userToSave);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        if (!userRepository.existsByCredentials_Username(username)) {
            throw new NotFoundException("The provided username doesn't exist.");
        }
        if (userRepository.findUserByCredentials_Username(username).isDeleted()) {
            throw new BadRequestException("The provided username was deleted.");
        }
        User userByCredentialsUsername = userRepository.findUserByCredentials_Username(username);

        return userMapper.entityToDto(userByCredentialsUsername);
    }

    @Override
    public UserResponseDto deleteUserByUsername(String username) {
        if (!userRepository.existsByCredentials_Username(username)) {
            throw new NotFoundException("The provided username doesn't exist.");
        }
        if (userRepository.findUserByCredentials_Username(username).isDeleted()) {
            throw new BadRequestException("The provided username is deleted already.");
        }
        User userToDelete = userRepository.findUserByCredentials_Username(username);
        userToDelete.setDeleted(true);
        userRepository.saveAndFlush(userToDelete);

        return userMapper.entityToDto(userToDelete);
    }

    @Override
    public UserResponseDto updateUserProfileByUsername(String username, UserRequestDto userRequestDto) {
        User updatedUser = userMapper.dtoToEntity(userRequestDto);
        if (!userRepository.existsByCredentials_Username(username)) {
            throw new NotFoundException("The provided username doesn't exist.");
        }
        if (!username.equals(updatedUser.getCredentials().getUsername())) {
            throw new NotAuthorizedException("The provided username doesn't match provided credentials.");
        }
        if (!userRepository.existsByCredentials(updatedUser.getCredentials())) {
            throw new NotAuthorizedException("The provided credentials don't match our records.");
        }
        if (userRepository.findUserByCredentials_Username(username).isDeleted()) {
            throw new BadRequestException("The provided username is deleted.");
        }
        if (updatedUser.getProfile().getEmail() == null){
            throw new BadRequestException("The provided email is null.");
        }
        User userToUpdate = userRepository.findUserByCredentials_Username(username);
        userToUpdate.setProfile(updatedUser.getProfile());
        userRepository.saveAndFlush(userToUpdate);

        return userMapper.entityToDto(userToUpdate);
    }

}
