package com.example.tasktracker.validations;

import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.custom.AlreadyExistsException;
import com.example.tasktracker.exceptions.custom.ValidationException;
import com.example.tasktracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserValidationTest {

  private UserRepository userRepository;
  private UserValidation userValidation;

  @BeforeEach
  void setUp() {
    userRepository = Mockito.mock(UserRepository.class);
    userValidation = new UserValidation(userRepository);
  }

  // ---------------------------------------------------------
  // Registration tests
  // ---------------------------------------------------------

  @Test
  void validateUserRegistration_ShouldThrow_WhenUserIsNull() {
    assertThrows(ValidationException.class,
            () -> userValidation.validateUserRegistration(null));
  }

  @Test
  void validateUserRegistration_ShouldThrow_WhenNameMissing() {
    UserRequestDto dto = new UserRequestDto("", "john@example.com", "password123".toCharArray());
    assertThrows(ValidationException.class,
            () -> userValidation.validateUserRegistration(dto));
  }

  @Test
  void validateUserRegistration_ShouldThrow_WhenEmailMissing() {
    UserRequestDto dto = new UserRequestDto("John", "", "password123".toCharArray());
    assertThrows(ValidationException.class,
            () -> userValidation.validateUserRegistration(dto));
  }

  @Test
  void validateUserRegistration_ShouldThrow_WhenEmailInvalid() {
    UserRequestDto dto = new UserRequestDto("John", "wrongEmail", "password123".toCharArray());
    assertThrows(ValidationException.class,
            () -> userValidation.validateUserRegistration(dto));
  }

  @Test
  void validateUserRegistration_ShouldThrow_WhenEmailAlreadyExists() {
    UserRequestDto dto = new UserRequestDto("John", "john@example.com", "password123".toCharArray());

    // FIX: Return Optional<User>, NOT Optional<Object>
    when(userRepository.findUserByEmail("john@example.com"))
            .thenReturn(Optional.of(new User()));

    assertThrows(AlreadyExistsException.class,
            () -> userValidation.validateUserRegistration(dto));

    verify(userRepository, times(1)).findUserByEmail("john@example.com");
  }

  @Test
  void validateUserRegistration_ShouldThrow_WhenPasswordMissing() {
    UserRequestDto dto = new UserRequestDto("John", "john@example.com", "".toCharArray());

    when(userRepository.findUserByEmail("john@example.com"))
            .thenReturn(Optional.empty());

    assertThrows(ValidationException.class,
            () -> userValidation.validateUserRegistration(dto));
  }

  @Test
  void validateUserRegistration_ShouldThrow_WhenPasswordTooShort() {
    UserRequestDto dto = new UserRequestDto("John", "john@example.com", "123".toCharArray());

    when(userRepository.findUserByEmail("john@example.com"))
            .thenReturn(Optional.empty());

    assertThrows(ValidationException.class,
            () -> userValidation.validateUserRegistration(dto));
  }

  @Test
  void validateUserRegistration_ShouldPass_WhenValid() {
    UserRequestDto dto = new UserRequestDto("John", "john@example.com", "password123".toCharArray());

    when(userRepository.findUserByEmail("john@example.com"))
            .thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userValidation.validateUserRegistration(dto));
  }

  // ---------------------------------------------------------
  // Login tests
  // ---------------------------------------------------------

  @Test
  void validateUserLogin_ShouldThrow_WhenEmailMissing() {
    assertThrows(ValidationException.class,
            () -> userValidation.validateUserLogin("", "password"));
  }

  @Test
  void validateUserLogin_ShouldThrow_WhenEmailInvalid() {
    assertThrows(ValidationException.class,
            () -> userValidation.validateUserLogin("badEmail", "password"));
  }

  @Test
  void validateUserLogin_ShouldThrow_WhenPasswordMissing() {
    assertThrows(ValidationException.class,
            () -> userValidation.validateUserLogin("john@example.com", ""));
  }

  @Test
  void validateUserLogin_ShouldPass_WhenValid() {
    assertDoesNotThrow(() ->
            userValidation.validateUserLogin("john@example.com", "password123"));
  }
}
