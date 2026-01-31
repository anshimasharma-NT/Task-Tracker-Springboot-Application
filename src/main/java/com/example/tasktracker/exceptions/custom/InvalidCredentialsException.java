package com.example.tasktracker.exceptions.custom;


/**
 * {@code AuthenticationException} is a custom runtime exception that indicates
 * that credentials are invalid.
 */
public class InvalidCredentialsException extends RuntimeException {

  /**
   * Constructs a new {@code AuthenticationException} with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception.
   */
  public InvalidCredentialsException(String message) {
    super(message);
  }
}
