package com.example.bitter.service.impl;

import com.example.bitter.dto.TweetResponseDto;
import com.example.bitter.dto.UserRequestDto;
import com.example.bitter.dto.UserResponseDto;
import com.example.bitter.entity.Tweet;
import com.example.bitter.entity.User;
import com.example.bitter.exception.BadRequestException;
import com.example.bitter.exception.NotAuthorizedException;
import com.example.bitter.exception.NotFoundException;
import com.example.bitter.mapper.TweetMapper;
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

    private final TweetMapper tweetMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getCredentials() == null) {
            throw new BadRequestException("Credentials can't be null.");
        }
        if (userRequestDto.getCredentials().getUsername() == null) {
            throw new BadRequestException("The provided username is null.");
        }
        if (userRequestDto.getCredentials().getPassword() == null) {
            throw new BadRequestException("The provided password is null.");
        }
        if (userRequestDto.getProfile() == null) {
            throw new BadRequestException("Profile can't be null.");
        }
        if (userRequestDto.getProfile().getEmail() == null) {
            throw new BadRequestException("The provided email is null.");
        }
        if (userRepository.existsByCredentials_Username(userRequestDto.getCredentials().getUsername())) {
            throw new BadRequestException("The username already exists.");
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
        if (updatedUser.getCredentials() == null) {
            throw new BadRequestException("Credentials can't be null.");
        }
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
        if (updatedUser.getProfile() == null) {
            throw new BadRequestException("Profile can't be null.");
        }
        User userToUpdate = userRepository.findUserByCredentials_Username(username);
        if (updatedUser.getProfile().getFirstName() == null) {
            updatedUser.getProfile().setFirstName(userToUpdate.getProfile().getFirstName());
        }
        if (updatedUser.getProfile().getLastName() == null) {
            updatedUser.getProfile().setLastName(userToUpdate.getProfile().getLastName());
        }
        if (updatedUser.getProfile().getEmail() == null) {
            updatedUser.getProfile().setEmail(userToUpdate.getProfile().getEmail());
        }
        if (updatedUser.getProfile().getPhone() == null) {
            updatedUser.getProfile().setPhone(userToUpdate.getProfile().getPhone());
        }
        userToUpdate.setProfile(updatedUser.getProfile());
        userRepository.saveAndFlush(userToUpdate);

        return userMapper.entityToDto(userToUpdate);
    }

    @Override
    public List<TweetResponseDto> getTweets(String username) {
        User user;
        try {
            user = userRepository.findUserByCredentials_Username(username);
        } catch (NotFoundException e) {
            throw e;
        }
        if (user == null) throw new NotFoundException("User " + username + " doesn't exist.");
        List<Tweet> tweets = user.getTweets();
        tweets.removeIf(Tweet::isDeleted);
        return tweetMapper.entitiesToDtos(tweets);
    }

//    // TODO: Test again after the users/@[username}/follow is done
//    @Override
//    public List<UserResponseDto> getFollowersOfTheUser(String username) {
//        User userByUsername = userRepository.findUserByCredentials_Username(username);
//        List<User> followers = userByUsername.getFollowers();
//
//        return userMapper.entitiesToDtos(followers);
//    }

//    @Override
//    public List<UserResponseDto> getUsersFollowedByUsername(String username) {
//        if (!userRepository.existsByCredentials_Username(username)) {
//            throw new NotFoundException("The provided username doesn't exist.");
//        }
//        if (userRepository.findUserByCredentials_Username(username).isDeleted()) {
//            throw new BadRequestException("The provided username is deleted.");
//        }
//        User userByUsername = userRepository.findUserByCredentials_Username(username);
//        List<User> usersFollowedByUsername = userRepository.findUsersByFollowersContaining(userByUsername);
//
//        return userMapper.entitiesToDtos(usersFollowedByUsername);
//    }

}
