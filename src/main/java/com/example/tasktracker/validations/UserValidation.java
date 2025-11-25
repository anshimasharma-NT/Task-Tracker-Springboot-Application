package com.example.tasktracker.validations;

import com.example.tasktracker.constants.UserConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.custom.AlreadyExistsException;
import com.example.tasktracker.exceptions.custom.InvalidCredentialsException;
import com.example.tasktracker.exceptions.custom.ValidationException;
import com.example.tasktracker.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class UserValidation {

  /**
   * Logger instance for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserValidation.class);

  /**
   * Password encoder used to securely hash passwords.
   */
  @Autowired
  private PasswordEncoder passwordEncoder;
  /**
   * Repository for performing operations on {@link User}.
   */
  @Autowired
  private UserRepository userRepository;
  /**
   * Validates the {@link UserRequestDto} object.
   * @param userRequestDto the registration request DTO containing user input.
   * @throws InvalidCredentialsException if any validation rule fails.
   */
  public void loginValidate(final UserRequestDto userRequestDto) {
    User user = userRepository.findByEmail(userRequestDto.getEmail());
    String rawPassword = new String(userRequestDto.getPassword());
    if (Objects.isNull(user) || !rawPassword.equals(user.getPassword())) {
      throw new InvalidCredentialsException("Invalid email or password");
    }
  }

  /**
   * Validates the {@link UserRequestDto} object.
   * @param userRequestDto the registration request DTO containing user input.
   * @throws AlreadyExistsException if any validation rule fails.
   */
  public void registerValidate(final UserRequestDto userRequestDto) {

    if (userRepository.existsByEmail(userRequestDto.getEmail())) {
      LOGGER.warn(UserConstants.USER_REGISTRATION_FAILED_EMAIL_EXISTS, userRequestDto.getEmail());
      throw new AlreadyExistsException(UserConstants.USER_EMAIL_ALREADY_EXISTS_MESSAGE);
    }
  }

}
