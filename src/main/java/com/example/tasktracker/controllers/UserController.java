package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.ErrorConstants;
import com.example.tasktracker.constants.SuccessConstants;
import com.example.tasktracker.constants.UrlConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.UserResponseDTo;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.custom.AuthenticationException;
import com.example.tasktracker.exceptions.custom.UserNotFoundException;
import com.example.tasktracker.mappers.UserMapper;
import com.example.tasktracker.services.UserService;
import com.example.tasktracker.validations.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;


@RestController
@RequestMapping(UrlConstants.USER)
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private UserValidation userValidation;

  @Autowired
  private UserMapper userMapper;

  @PostMapping(UrlConstants.REGISTER)
  public ResponseEntity<UserResponseDTo> register(@RequestBody UserRequestDto userDto) {

    userValidation.validateUserRegistration(userDto);

    User user = userMapper.toEntity(userDto);

    User savedUser = userService.storeUserData(user);

    if(savedUser == null){
      throw new UserNotFoundException(ErrorConstants.ERROR_REGISTER_MESSAGE);
    }

    UserResponseDTo responseDto = userMapper.toResponseDto(savedUser);

    LOGGER.info(SuccessConstants.USER_REGISTER_SUCCESS, responseDto.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @PostMapping(UrlConstants.LOGIN)
  public ResponseEntity<UserResponseDTo> login(@RequestBody UserRequestDto userDto) {

    String rawPassword = new String(userDto.getPassword());

    userValidation.validateUserLogin(userDto.getEmail(), rawPassword);

    User loggedInUser = userService.getUserByEmailAndPassword(
            userDto.getEmail(), rawPassword);

    if (loggedInUser == null) {
      throw new AuthenticationException(ErrorConstants.ERROR_LOGIN_MESSAGE);
    }

    UserResponseDTo responseDto = userMapper.toResponseDto(loggedInUser);

    LOGGER.info(SuccessConstants.USER_LOGIN_SUCCESS, responseDto.getEmail());
    return ResponseEntity.ok(responseDto);
  }

}
