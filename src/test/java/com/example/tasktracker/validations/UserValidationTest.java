package com.example.tasktracker.validations;

import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.custom.AlreadyExistsException;
import com.example.tasktracker.exceptions.custom.InvalidCredentialsException;
import com.example.tasktracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Tests for {@link UserValidation}.
 */
@ExtendWith(MockitoExtension.class)
class UserValidationTest {
  /**
   * Mocked repository used to check whether an email already exists.
   */
  @Mock
  private UserRepository userRepository;
  /**
   * Validator instance under test, with mocks injected.
   */
  @InjectMocks
  private UserValidation userValidation;
  /**
   * Sample login DTO used in validation tests.
   */
  private UserRequestDto userRequestDto;

  @BeforeEach
  public void init() {
    userRequestDto = new UserRequestDto();
    userRequestDto.setName(("Anshima Sharma"));
    userRequestDto.setEmail("anshima@0906@nucleusteq.com");
    userRequestDto.setPassword(
            "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72".toCharArray()
    );
  }

  @Test
  public void testLoginValidateSuccessful() {
    User user = new User();
    user.setEmail("anshima0906@nucleusteq.com");
    user.setPassword(
            "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72"
    );
    when(userRepository.findByEmail(userRequestDto.getEmail())).thenReturn(user);
    assertDoesNotThrow(() -> userValidation.loginValidate(userRequestDto));

    verify(userRepository, times(1))
            .findByEmail(userRequestDto.getEmail());
  }

  @Test
  public void testLoginValidateFailedEmailNotFound() {
    when(userRepository.findByEmail(userRequestDto.getEmail()))
            .thenReturn(null);

    assertThrows(InvalidCredentialsException.class,
            () -> userValidation.loginValidate(userRequestDto));

    verify(userRepository, times(1))
            .findByEmail(userRequestDto.getEmail());
  }

  @Test
  public void testLoginValidateFailedIncorrectPassword() {
    User user = new User();
    user.setEmail("anshima0906@nucleusteq.com");
    user.setPassword("wrong-password");

    when(userRepository.findByEmail(userRequestDto.getEmail()))
            .thenReturn(user);


    assertThrows(InvalidCredentialsException.class,
            () -> userValidation.loginValidate(userRequestDto));

    verify(userRepository, times(1))
            .findByEmail(userRequestDto.getEmail());
  }

  /**
   * Ensures validation passes when email does not exist.
   */
  @Test
  public void testRegisterValidateSuccessful() {
    when(userRepository.existsByEmail(userRequestDto.getEmail())).thenReturn(false);
    assertDoesNotThrow(() -> userValidation.registerValidate(userRequestDto));
    verify(userRepository, times(1)).existsByEmail(userRequestDto.getEmail());
  }

  /**
   * Ensures validation fails when email already exists.
   */
  @Test
  public void testRegisterValidateFailed() {
    when(userRepository.existsByEmail(userRequestDto.getEmail())).thenReturn(true);
    assertThrows(AlreadyExistsException.class,
            () -> userValidation.registerValidate(userRequestDto));
    verify(userRepository, times(1)).existsByEmail(userRequestDto.getEmail());
  }
}
