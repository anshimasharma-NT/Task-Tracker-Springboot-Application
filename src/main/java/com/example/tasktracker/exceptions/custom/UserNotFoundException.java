package com.example.tasktracker.exceptions.custom;


/**
 * {@code UserNotFoundException} is a custom runtime exception that indicates
 * that a requested entity was not found in the data source.
 */
public class UserNotFoundException extends RuntimeException {

  /**
   * Constructs a new {@code UserNotFoundException} with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception.
   */
  public UserNotFoundException(String message) {
    super(message);
  }
}