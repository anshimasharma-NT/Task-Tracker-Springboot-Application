package com.example.tasktracker.serviceImpl;

import com.example.tasktracker.entities.User;
import com.example.tasktracker.repositories.UserRepository;
import com.example.tasktracker.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  // -----------------------------------------------------------------------
  // getUserByEmailAndPassword() Tests
  // -----------------------------------------------------------------------

  @Test
  void getUserByEmailAndPassword_ShouldReturnNull_WhenUserNotFound() {
    when(userRepository.findUserByEmail("test@example.com"))
            .thenReturn(Optional.empty());

    User result = userService.getUserByEmailAndPassword("test@example.com", "123");

    assertNull(result);
    verify(userRepository, times(1)).findUserByEmail("test@example.com");
  }

  @Test
  void getUserByEmailAndPassword_ShouldReturnNull_WhenPasswordDoesNotMatch() {
    User user = new User();
    user.setPassword("encodedPass");

    when(userRepository.findUserByEmail("test@example.com"))
            .thenReturn(Optional.of(user));

    when(passwordEncoder.matches("123", "encodedPass")).thenReturn(false);

    User result = userService.getUserByEmailAndPassword("test@example.com", "123");

    assertNull(result);
    verify(passwordEncoder, times(1)).matches("123", "encodedPass");
  }

  @Test
  void getUserByEmailAndPassword_ShouldReturnUser_WhenCredentialsMatch() {
    User user = new User();
    user.setPassword("encodedPass");

    when(userRepository.findUserByEmail("test@example.com"))
            .thenReturn(Optional.of(user));

    when(passwordEncoder.matches("123", "encodedPass"))
            .thenReturn(true);

    User result = userService.getUserByEmailAndPassword("test@example.com", "123");

    assertNotNull(result);
    assertEquals(user, result);

    verify(passwordEncoder, times(1)).matches("123", "encodedPass");
  }

  // -----------------------------------------------------------------------
  // getById() Tests
  // -----------------------------------------------------------------------

  @Test
  void getById_ShouldReturnNull_WhenNotFound() {
    when(userRepository.findById(1))
            .thenReturn(Optional.empty());

    User result = userService.getById(1L);

    assertNull(result);
    verify(userRepository, times(1)).findById(1);
  }

  @Test
  void getById_ShouldReturnUser_WhenFound() {
    User user = new User();
    when(userRepository.findById(1))
            .thenReturn(Optional.of(user));

    User result = userService.getById(1L);

    assertNotNull(result);
    assertEquals(user, result);
  }

  // -----------------------------------------------------------------------
  // storeUserData() Tests
  // -----------------------------------------------------------------------

  @Test
  void storeUserData_ShouldStoreRawPassword_AndSaveUser() {
    // Arrange
    User user = new User();
    user.setPassword("rawPass");

    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    User savedUser = userService.storeUserData(user);

    // Assert
    assertNotNull(savedUser);
    assertEquals("rawPass", savedUser.getPassword());

    // Verify that encode() is NOT called because implementation does not encode
    verify(passwordEncoder, never()).encode(anyString());

    // Verify save() is called
    verify(userRepository, times(1)).save(user);
  }

}
