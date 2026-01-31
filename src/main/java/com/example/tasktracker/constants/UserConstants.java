package com.example.tasktracker.constants;

/**
 * Contains constant values used across authentication-related components.
 */
public interface UserConstants
{
  /**
   * No-op default method to prevent interface from being empty.
   */
  default void constantInterface() {
  }
  /**
   * Log message when a registration request is received.
   */
  String RECEIVED_REQUEST_FOR_REGISTER =
          "Received request to register user with email: {}";
  /**
   * Log message when user registration completes successfully.
   */
  String USER_REGISTERED_SUCCESSFULLY =
          "User registered successfully with email: {}";
  /**
   * Log message when user registration fails due to existing email.
   */
  String USER_REGISTRATION_FAILED_EMAIL_EXISTS =
          "Registration failed: user with email {} already exists.";
  /**
   * Message returned when user registration is successful.
   */
  String USER_REGISTRATION_SUCCESS_MESSAGE =
          "User registered successfully.";
  /**
   * Message returned when user login is successful.
   */
  String USER_LOGIN_SUCCESS_MESSAGE =
          "User logged in successfully!.";
  /**
   * Message returned when registration fails due to duplicate email.
   */
  String USER_EMAIL_ALREADY_EXISTS_MESSAGE =
          "A user with this email address already exists.";
  /**
   * Message returned when user not exits in database.
   */
  String USER_NOT_FOUND_MESSAGE =
          "A user with this userId not exists.";
  /**
   * Log message when a login request is received.
   */
  String RECEIVED_REQUEST_FOR_LOGIN =
          "Received request to login user with email: {}";
  /**
   * Log message when user login successfully.
   */
  String USER_LOGIN_SUCCESSFULLY =
          "User login successfully with email: {}";

}
