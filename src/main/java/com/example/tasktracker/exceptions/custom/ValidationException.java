package com.example.tasktracker.exceptions.custom;

/**
 * {@code ValidationException} is a custom runtime exception that indicates
 * there exists a validation error.
 */
public class ValidationException extends RuntimeException {

  /**
   * Constructs a new {@code ValidationException} with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception.
   */
  public ValidationException(final String message) {

    super(message);
  }
}
