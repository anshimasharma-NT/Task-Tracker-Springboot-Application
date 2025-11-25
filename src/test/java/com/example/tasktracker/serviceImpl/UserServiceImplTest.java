package com.example.tasktracker.serviceImpl;


import com.example.tasktracker.constants.UserConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link UserServiceImpl#registerUser(UserRequestDto)}.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  /**
   * Mocked repository for saving users.
   */
  @Mock
  private UserRepository userRepository;

  /**
   * Mocked password encoder for encoding passwords.
   */
  @Mock
  private PasswordEncoder passwordEncoder;

  /**
   * Service instance under test with injected mocks.
   */
  @InjectMocks
  private UserServiceImpl userService;

  /**
   * Sample registration DTO used in tests.
   */
  private UserRequestDto userRequestDto;

  /**
   * Sets up a sample registration request before each test.
   */
  @BeforeEach
  void setUp() {
    userRequestDto = new UserRequestDto();
    userRequestDto.setName("Anshima");
    userRequestDto.setEmail("anshima@nucleusteq.com");
    userRequestDto.setPassword("password123".toCharArray());

    userRequestDto = new UserRequestDto();
    userRequestDto.setEmail("anshima@nucleusteq.com");
    userRequestDto.setPassword("password123".toCharArray());
  }

  /**
   * Tests successful registration.
   */
  @Test
  public void testRegister() {
    String encodedPassword = "encodedPassword";

    when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
    when(userRepository.save(any(User.class))).thenReturn(new User());
    ApiResponseDto response = userService.registerUser(userRequestDto);

    assertTrue(response.isSuccess());
    assertEquals(UserConstants.USER_REGISTRATION_SUCCESS_MESSAGE, response.getMessage());
    verify(passwordEncoder).encode("password123");
    verify(userRepository).save(any(User.class));
  }
  /**
   * Test successful Login.
   */
  @Test
  public void testLogin() {
    when(userRepository.findByEmail(userRequestDto.getEmail())).thenReturn(new User());
    ApiResponseDto response = userService.loginUser(userRequestDto);
    assertTrue(response.isSuccess());
    assertEquals(UserConstants.USER_LOGIN_SUCCESS_MESSAGE, response.getMessage());
    assertTrue(response.isSuccess());
    assertEquals("User logged in successfully!.", response.getMessage());
  }
}
