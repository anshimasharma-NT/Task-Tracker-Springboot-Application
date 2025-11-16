package com.example.tasktracker.exceptions.custom;

/**
 * Custom exception thrown when validation fails in the validation layer.
 *
 * <p>This allows the application to differentiate between validation
 * errors and other types of exceptions.</p>
 */
public class ValidationException extends RuntimeException {

  /**
   * Creates a new ValidationException with a custom message.
   *
   * @param message the detail message describing the validation failure
   */
  public ValidationException(final String message) {
    super(message);
  }
}
