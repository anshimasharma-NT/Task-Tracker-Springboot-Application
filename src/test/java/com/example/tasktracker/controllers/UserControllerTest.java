package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.ErrorConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.UserResponseDTo;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.custom.AuthenticationException;
import com.example.tasktracker.exceptions.custom.UserNotFoundException;
import com.example.tasktracker.mappers.UserMapper;
import com.example.tasktracker.services.UserService;
import com.example.tasktracker.validations.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @Mock
  private UserValidation userValidation;

  @Mock
  private UserMapper userMapper;

  private UserRequestDto validUserRequest;
  private User userEntity;
  private UserResponseDTo userResponseDto;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    validUserRequest = new UserRequestDto("John Doe", "john@example.com", "password123".toCharArray());

    userEntity = User.builder()
            .id(1L)
            .name("John Doe")
            .email("john@example.com")
            .password("encodedPassword")
            .build();

    userResponseDto = new UserResponseDTo(1L, "John Doe", "john@example.com");
  }

  // ------------------- REGISTER -------------------

  @Test
  void registerShouldReturnCreatedWhenUserIsValid() {
    doNothing().when(userValidation).validateUserRegistration(validUserRequest);
    when(userMapper.toEntity(validUserRequest)).thenReturn(userEntity);
    when(userService.storeUserData(userEntity)).thenReturn(userEntity);
    when(userMapper.toResponseDto(userEntity)).thenReturn(userResponseDto);

    ResponseEntity<UserResponseDTo> response = userController.register(validUserRequest);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(userResponseDto, response.getBody());
    verify(userValidation, times(1)).validateUserRegistration(validUserRequest);
    verify(userService, times(1)).storeUserData(userEntity);
  }

  @Test
  void registerShouldThrowUserNotFoundExceptionWhenUserServiceReturnsNull() {
    doNothing().when(userValidation).validateUserRegistration(validUserRequest);
    when(userMapper.toEntity(validUserRequest)).thenReturn(userEntity);
    when(userService.storeUserData(userEntity)).thenReturn(null);

    UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
            userController.register(validUserRequest));

    assertEquals(ErrorConstants.ERROR_REGISTER_MESSAGE, exception.getMessage());
    verify(userValidation, times(1)).validateUserRegistration(validUserRequest);
    verify(userService, times(1)).storeUserData(userEntity);
  }

  @Test
  void registerShouldThrowValidationExceptionWhenValidationFails() {
    doThrow(new IllegalArgumentException("Invalid input"))
            .when(userValidation).validateUserRegistration(validUserRequest);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            userController.register(validUserRequest));

    assertEquals("Invalid input", exception.getMessage());
    verify(userValidation, times(1)).validateUserRegistration(validUserRequest);
    verify(userService, never()).storeUserData(any());
  }

  // ------------------- LOGIN -------------------

  @Test
  void loginShouldReturnOkWhenCredentialsAreValid() {
    doNothing().when(userValidation)
            .validateUserLogin(validUserRequest.getEmail(), Arrays.toString(validUserRequest.getPassword()));

    when(userService.getUserByEmailAndPassword(
            validUserRequest.getEmail(),
            Arrays.toString(validUserRequest.getPassword())
    )).thenReturn(userEntity);

    when(userMapper.toResponseDto(userEntity)).thenReturn(userResponseDto);

    ResponseEntity<UserResponseDTo> response = userController.login(validUserRequest);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userResponseDto, response.getBody());
    verify(userValidation, times(1))
            .validateUserLogin(validUserRequest.getEmail(), Arrays.toString(validUserRequest.getPassword()));
    verify(userService, times(1))
            .getUserByEmailAndPassword(validUserRequest.getEmail(), Arrays.toString(validUserRequest.getPassword()));
  }

  @Test
  void loginShouldThrowAuthenticationExceptionWhenUserNotFound() {
    doNothing().when(userValidation)
            .validateUserLogin(validUserRequest.getEmail(), Arrays.toString(validUserRequest.getPassword()));

    when(userService.getUserByEmailAndPassword(
            validUserRequest.getEmail(),
            Arrays.toString(validUserRequest.getPassword())
    )).thenReturn(null);

    AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
            userController.login(validUserRequest));

    assertEquals(ErrorConstants.ERROR_LOGIN_MESSAGE, exception.getMessage());
    verify(userValidation, times(1))
            .validateUserLogin(validUserRequest.getEmail(), Arrays.toString(validUserRequest.getPassword()));
    verify(userService, times(1))
            .getUserByEmailAndPassword(validUserRequest.getEmail(), Arrays.toString(validUserRequest.getPassword()));
  }

  @Test
  void loginShouldThrowValidationExceptionWhenValidationFails() {
    doThrow(new IllegalArgumentException("Invalid input"))
            .when(userValidation)
            .validateUserLogin(validUserRequest.getEmail(), Arrays.toString(validUserRequest.getPassword()));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            userController.login(validUserRequest));

    assertEquals("Invalid input", exception.getMessage());
    verify(userValidation, times(1))
            .validateUserLogin(validUserRequest.getEmail(), Arrays.toString(validUserRequest.getPassword()));
    verify(userService, never()).getUserByEmailAndPassword(any(), any());
  }
}
