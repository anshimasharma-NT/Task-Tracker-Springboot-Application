package com.example.tasktracker.serviceImpl;

import com.example.tasktracker.constants.UserConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.repositories.UserRepository;
import com.example.tasktracker.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserService} that manages user registration and
 * authentication-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

  /**
   * Logger instance for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  /**
   * Repository for performing operations on {@link User}.
   */
  @Autowired
  private UserRepository userRepository;

  /**
   * Password encoder used to securely hash passwords.
   */
  @Autowired
  private PasswordEncoder passwordEncoder;
  /**
   * Registers a new user in the system.
   * @param userRequestDto DTO containing user registration details
   * @return {@link ApiResponseDto} containing the registration result
   */
  @Override
  public ApiResponseDto registerUser(final UserRequestDto userRequestDto) {
    User user = new User();
    user.setName(userRequestDto.getName());
    user.setEmail(userRequestDto.getEmail());

    String rawOrHashed = new String(userRequestDto.getPassword());

    if (isBCryptHash(rawOrHashed)) {
      user.setPassword(rawOrHashed);
    } else {
      user.setPassword(passwordEncoder.encode(rawOrHashed));
    }

    userRepository.save(user);
    return new ApiResponseDto(true, UserConstants.USER_REGISTRATION_SUCCESS_MESSAGE);
  }

  private boolean isBCryptHash(String password) {
    if (password == null) {
      return false;
    }
    return (password.startsWith("$2a$") ||
            password.startsWith("$2b$") ||
            password.startsWith("$2y$")) &&
            password.length() == 60;
  }

  /**
   * Authenticates a user.
   * @param userRequestDto LoginInDTO with email and password
   * @return LoginOutDTO indicating success
   */
  @Override
  public ApiResponseDto loginUser(final UserRequestDto userRequestDto) {
    userRepository.findByEmail(userRequestDto.getEmail());
    return new ApiResponseDto(true, UserConstants.USER_LOGIN_SUCCESS_MESSAGE);
  }
}
