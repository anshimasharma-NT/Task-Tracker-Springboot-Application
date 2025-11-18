package com.example.tasktracker.validations;

import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.custom.AlreadyExistsException;
import com.example.tasktracker.exceptions.custom.ValidationException;
import com.example.tasktracker.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidation {

  private final UserRepository userRepository;

  private static final Pattern EMAIL_PATTERN =
          Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

  public UserValidation(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // -----------------------------------------------------
  // Registration Validation
  // -----------------------------------------------------
  public void validateUserRegistration(UserRequestDto dto) {

    if (dto == null) {
      throw new ValidationException("User cannot be null");
    }

    if (dto.getName() == null || dto.getName().trim().isEmpty()) {
      throw new ValidationException("Name is required");
    }

    if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
      throw new ValidationException("Email is required");
    }

    if (!EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
      throw new ValidationException("Invalid email format");
    }

    userRepository.findUserByEmail(dto.getEmail())
            .ifPresent(u -> {
              throw new AlreadyExistsException("Email already exists");
            });

    char[] password = dto.getPassword();

    if (password == null || password.length == 0) {
      throw new ValidationException("Password is required");
    }

    if (password.length < 6) {
      throw new ValidationException("Password must be at least 6 characters long");
    }
  }

  // -----------------------------------------------------
  // Login Validation
  // -----------------------------------------------------
  public void validateUserLogin(String email, String password) {

    if (email == null || email.trim().isEmpty()) {
      throw new ValidationException("Email is required");
    }

    if (!EMAIL_PATTERN.matcher(email).matches()) {
      throw new ValidationException("Invalid email format");
    }

    if (password == null || password.trim().isEmpty()) {
      throw new ValidationException("Password is required");
    }
  }
}
