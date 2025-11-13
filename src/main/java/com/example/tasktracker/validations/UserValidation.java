package com.example.tasktracker.validations;

import com.example.tasktracker.constants.NumericConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.ValidationException;
import com.example.tasktracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validation layer for {@link User} entities.
 * <p>
 * This class centralizes all user input validation logic
 * for registration and login operations.
 * </p>
 */
@Component
public final class UserValidation {

  /** Repository for checking existing users (to prevent duplicates). */
  @Autowired
  private final UserRepository userRepository;

  /**
   * Constructor-based dependency injection.
   *
   * @param userRepository repository used for checking existing users
   */
  public UserValidation(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Validates user input during registration.
   * <ul>
   *   <li>Ensures that the user object is not null.</li>
   *   <li>Validates that the name, email, and password fields are not empty.</li>
   *   <li>Ensures that the email format is valid.</li>
   *   <li>Checks if the email is not already registered in the database.</li>
   *   <li>Validates that the password meets minimum length requirements.</li>
   * </ul>
   *
   * @param user the user object to validate
   * @throws ValidationException if any validation rule fails
   */
  public void validateUserRegistration(final UserRequestDto user) {
    if (user == null) {
      throw new ValidationException("User object cannot be null.");
    }

    if (!StringUtils.hasText(user.getName())) {
      throw new ValidationException("Name is required.");
    }

    if (!StringUtils.hasText(user.getEmail())) {
      throw new ValidationException("Email is required.");
    }

    if (isValidEmail(user.getEmail())) {
      throw new ValidationException("Invalid email format.");
    }

    if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
      throw new ValidationException("Email already exists. Please use another one.");
    }

    if (!StringUtils.hasText(user.getPassword())) {
      throw new ValidationException("Password is required.");
    }

    if (user.getPassword().length() < NumericConstants.PASSWORD_MIN_LENGTH) {
      throw new ValidationException("Password must be at least 8 characters long.");
    }
  }

  /**
   * Validates user credentials during login.
   * <ul>
   *   <li>Ensures that email and password are not empty.</li>
   *   <li>Ensures that the email format is valid.</li>
   * </ul>
   *
   * @param email user's email
   * @param password user's password
   * @throws ValidationException if the credentials are invalid
   */
  public void validateUserLogin(final String email, final String password) {
    if (!StringUtils.hasText(email)) {
      throw new ValidationException("Email cannot be empty.");
    }

    if (isValidEmail(email)) {
      throw new ValidationException("Invalid email format.");
    }

    if (!StringUtils.hasText(password)) {
      throw new ValidationException("Password cannot be empty.");
    }
  }

  /**
   * Utility method to validate the format of an email address.
   *
   * @param email the email string to validate
   * @return {@code true} if the email matches the required format, {@code false} otherwise
   */
  private boolean isValidEmail(final String email) {
    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return !email.matches(regex);
  }
}
