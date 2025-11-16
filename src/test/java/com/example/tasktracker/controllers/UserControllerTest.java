package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.ErrorConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.UserResponseDTo;
import com.example.tasktracker.entities.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UserController}.
 *
 * <p>This test class verifies the behavior of UserController endpoints
 * for registering and logging in users. All external dependencies
 * are mocked to isolate controller logic.</p>
 */
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

    validUserRequest = new UserRequestDto("John Doe", "john@example.com", "password123");
    userEntity = User.builder()
            .id(1L)
            .name("John Doe")
            .email("john@example.com")
            .password("encodedPassword")
            .build();
    userResponseDto = new UserResponseDTo(1L, "John Doe", "john@example.com");
  }

  /**
   * Test successful user registration.
   */
  @Test
  void registerShouldReturnCreatedWhenUserIsValid() {
    // Arrange
    doNothing().when(userValidation).validateUserRegistration(validUserRequest);
    when(userMapper.toEntity(validUserRequest)).thenReturn(userEntity);
    when(userService.storeUserData(userEntity)).thenReturn(userEntity);
    when(userMapper.toResponseDto(userEntity)).thenReturn(userResponseDto);

    // Act
    ResponseEntity<?> response = userController.register(validUserRequest);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(userResponseDto, response.getBody());
    verify(userValidation, times(1)).validateUserRegistration(validUserRequest);
    verify(userService, times(1)).storeUserData(userEntity);
  }

  /**
   * Test user registration when validation fails.
   */
  @Test
  void registerShouldReturnBadRequestWhenValidationFails() {
    // Arrange
    doThrow(new IllegalArgumentException("Invalid input"))
            .when(userValidation).validateUserRegistration(validUserRequest);

    // Act
    ResponseEntity<?> response = userController.register(validUserRequest);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid input", response.getBody());
    verify(userValidation, times(1)).validateUserRegistration(validUserRequest);
    verify(userService, never()).storeUserData(any());
  }

  /**
   * Test user login success.
   */
  @Test
  void loginShouldReturnOkWhenCredentialsAreValid() {
    // Arrange
    doNothing().when(userValidation).validateUserLogin(validUserRequest.getEmail(), validUserRequest.getPassword());
    when(userService.getUserByEmailAndPassword(validUserRequest.getEmail(), validUserRequest.getPassword()))
            .thenReturn(userEntity);
    when(userMapper.toResponseDto(userEntity)).thenReturn(userResponseDto);

    // Act
    ResponseEntity<?> response = userController.login(validUserRequest);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userResponseDto, response.getBody());
    verify(userValidation, times(1))
            .validateUserLogin(validUserRequest.getEmail(), validUserRequest.getPassword());
    verify(userService, times(1))
            .getUserByEmailAndPassword(validUserRequest.getEmail(), validUserRequest.getPassword());
  }

  /**
   * Test user login with invalid credentials.
   */
  @Test
  void loginShouldReturnUnauthorizedWhenCredentialsAreInvalid() {
    // Arrange
    doNothing().when(userValidation).validateUserLogin(validUserRequest.getEmail(), validUserRequest.getPassword());
    when(userService.getUserByEmailAndPassword(validUserRequest.getEmail(), validUserRequest.getPassword()))
            .thenReturn(null);

    // Act
    ResponseEntity<?> response = userController.login(validUserRequest);

    // Assert
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Invalid email or password", response.getBody());
    verify(userValidation, times(1))
            .validateUserLogin(validUserRequest.getEmail(), validUserRequest.getPassword());
    verify(userService, times(1))
            .getUserByEmailAndPassword(validUserRequest.getEmail(), validUserRequest.getPassword());
  }

  /**
   * Test login when validation fails.
   */
  @Test
  void loginShouldReturnBadRequestWhenValidationFails() {
    // Arrange
    doThrow(new IllegalArgumentException("Invalid input"))
            .when(userValidation).validateUserLogin(validUserRequest.getEmail(), validUserRequest.getPassword());

    // Act
    ResponseEntity<?> response = userController.login(validUserRequest);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid input", response.getBody());
    verify(userValidation, times(1))
            .validateUserLogin(validUserRequest.getEmail(), validUserRequest.getPassword());
    verify(userService, never()).getUserByEmailAndPassword(any(), any());
  }
}
