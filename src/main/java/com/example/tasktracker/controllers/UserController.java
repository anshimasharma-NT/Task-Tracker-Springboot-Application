package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.SuccessConstants;
import com.example.tasktracker.constants.UrlConstants;
import com.example.tasktracker.constants.ErrorConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.UserResponseDTo;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.mappers.UserMapper;
import com.example.tasktracker.services.UserService;
import com.example.tasktracker.validations.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling user registration and login.
 * <p>
 * Provides endpoints for registering and authenticating users.
 * </p>
 */
@RestController
@RequestMapping(UrlConstants.USER)
public class UserController {

  /** Logger instance for this controller. */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  /** Service handling business logic for user operations. */
  @Autowired
  private UserService userService;

  /** Utility for validating user-related inputs. */
  @Autowired
  private UserValidation userValidation;

  /** Mapper for converting between User entities and DTOs. */
  @Autowired
  private UserMapper userMapper;

  /**
   * Handles registration of a new user.
   *
   * @param userDto the user registration data received from the request body
   * @return {@link ResponseEntity} containing a {@link UserResponseDTo}
   *         if registration is successful, or an error message otherwise
   */
  @PostMapping(UrlConstants.REGISTER)
  public ResponseEntity<?> register(final @RequestBody UserRequestDto userDto) {
    try {
      // Validate user input
      userValidation.validateUserRegistration(userDto);

      // Convert DTO -> Entity
      User user = userMapper.toEntity(userDto);

      // Save to DB
      User savedUser = userService.storeUserData(user);

      // Convert Entity -> DTO (for response)
      UserResponseDTo responseDto = userMapper.toResponseDto(savedUser);

      LOGGER.info(SuccessConstants.USER_REGISTER_SUCCESS, responseDto.getEmail());
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    } catch (IllegalArgumentException e) {
      LOGGER.warn("Validation failed during registration: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      LOGGER.error(ErrorConstants.ERROR_REGISTER_MESSAGE, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(ErrorConstants.ERROR_REGISTER_MESSAGE + e.getMessage());
    }
  }

  /**
   * Handles user login requests.
   *
   * @param userDto the user login credentials (email and password)
   * @return {@link ResponseEntity} containing a {@link UserResponseDTo}
   *         if login is successful, or an error message if authentication fails
   */
  @PostMapping(UrlConstants.LOGIN)
  public ResponseEntity<?> login(final @RequestBody UserRequestDto userDto) {
    try {
      // Validate user input
      userValidation.validateUserLogin(userDto.getEmail(), userDto.getPassword());

      // Authenticate the user
      User loggedInUser = userService.getUserByEmailAndPassword(
              userDto.getEmail(), userDto.getPassword());

      if (loggedInUser != null) {
        // Convert Entity -> DTO
        UserResponseDTo responseDto = userMapper.toResponseDto(loggedInUser);

        LOGGER.info("User logged in successfully: {}", responseDto.getEmail());
        return ResponseEntity.ok(responseDto);
      } else {
        LOGGER.warn("Invalid email or password for: {}", userDto.getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
      }

    } catch (IllegalArgumentException e) {
      LOGGER.warn("Validation failed during login: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      LOGGER.error("Login failed", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Something went wrong: " + e.getMessage());
    }
  }
}
